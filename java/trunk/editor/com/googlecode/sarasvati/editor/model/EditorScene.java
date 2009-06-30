/*
    This file is part of Sarasvati.

    Sarasvati is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    Sarasvati is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with Sarasvati.  If not, see <http://www.gnu.org/licenses/>.

    Copyright 2008-2009 Paul Lorenz
*/
package com.googlecode.sarasvati.editor.model;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.JLabel;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import com.googlecode.sarasvati.JoinType;
import com.googlecode.sarasvati.editor.EditorMode;
import com.googlecode.sarasvati.editor.GraphEditor;
import com.googlecode.sarasvati.editor.MoveTrackAction;
import com.googlecode.sarasvati.editor.NodePropertiesAction;
import com.googlecode.sarasvati.editor.SceneAddNodeAction;
import com.googlecode.sarasvati.editor.command.CommandStack;
import com.googlecode.sarasvati.visual.common.GraphSceneImpl;
import com.googlecode.sarasvati.visual.common.NodeDrawConfig;
import com.googlecode.sarasvati.visual.common.PathTrackingConnectionWidget;
import com.googlecode.sarasvati.visual.icon.DefaultNodeIcon;
import com.googlecode.sarasvati.visual.icon.TaskIcon;

public class EditorScene extends GraphSceneImpl<EditorGraphMember<?>, EditorArc>
{
  protected final CommandStack commandStack;
  protected final EditorGraph graph;

  private final WidgetAction moveAction = new MoveTrackAction( ActionFactory.createAlignWithMoveAction( mainLayer, intrLayer, null ) );
  private final WidgetAction connectAction = ActionFactory.createConnectAction( intrLayer, new SceneConnectProvider() );
  private final WidgetAction reconnectAction = ActionFactory.createReconnectAction( new SceneReconnectProvider() );

  private final WidgetAction nodePropertiesAction = new NodePropertiesAction();

  private boolean loading = true;

  public EditorScene (EditorGraph graph)
  {
    this.graph = graph;
    this.commandStack = new CommandStack();

    getActions().addAction( SceneAddNodeAction.INSTANCE );

    for ( EditorGraphMember<?> member : graph.getNodes() )
    {
      addNode( member );
    }

    for ( EditorGraphMember<?> member : graph.getExternals() )
    {
      addNode( member );
    }

    for ( EditorArc arc : graph.getArcs() )
    {
      addEdge( arc );
      setEdgeSource( arc, arc.getStart() );
      setEdgeTarget( arc, arc.getEnd() );
    }

    loading = false;
  }

  public CommandStack getCommandStack ()
  {
    return commandStack;
  }

  public EditorGraph getGraph ()
  {
    return graph;
  }

  public void modeAddNode ()
  {
    for ( Widget widget : mainLayer.getChildren() )
    {
      widget.getActions().addAction( connectAction );
    }
  }

  public void modeMove ()
  {
    for ( Widget widget : mainLayer.getChildren() )
    {
      widget.getActions().removeAction( connectAction );
    }
  }

  @Override
  protected PathTrackingConnectionWidget attachEdgeWidget (EditorArc edge)
  {
    PathTrackingConnectionWidget widget = super.attachEdgeWidget( edge );
    widget.setEndPointShape (PointShape.SQUARE_FILLED_BIG);
    widget.getActions().addAction( createObjectHoverAction() );
    widget.getActions().addAction( createSelectAction() );
    widget.getActions().addAction( reconnectAction );
    return widget;
  }

  protected Icon getIconForMember (final EditorGraphMember<?> node)
  {
    boolean join = false;
    boolean isTask = false;

    if ( node instanceof EditorNode )
    {
      join = ((EditorNode)node).getState().getJoinType() != JoinType.OR;
      isTask = "task".equalsIgnoreCase( ((EditorNode)node).getState().getType() );
    }

    Icon icon = isTask ? new TaskIcon( node.getState().getName(), NodeDrawConfig.NODE_BG_COMPLETED, join  ) :
                         new DefaultNodeIcon( node.getState().getName(), NodeDrawConfig.NODE_BG_COMPLETED, join );

    return icon;
  }

  @Override
  protected Widget widgetForNode (final EditorGraphMember<?> node)
  {
    final Icon icon = getIconForMember( node );

    final JLabel label = new JLabel( icon );
    final ComponentWidget widget = new ComponentWidget( this, label );

    if ( !loading )
    {
      int xOffset = icon.getIconWidth() >> 1;
      int yOffset = icon.getIconHeight() >> 1;

      node.setX( node.getX() - xOffset );
      node.setY( node.getY() - yOffset );
    }

    widget.setPreferredLocation( node.getOrigin() );

    widget.getActions().addAction( nodePropertiesAction );
    widget.getActions().addAction( moveAction );

    if ( GraphEditor.getInstance().getMode() == EditorMode.AddNode )
    {
      widget.getActions().addAction( connectAction );
    }

    node.addListener( new ModelListener<EditorGraphMember<?>> ()
    {
      @Override
      public void modelChanged (EditorGraphMember<?> modelInstance)
      {
        label.setIcon( getIconForMember( node ) );
      }
    });

    return widget;
  }

  public class SceneConnectProvider implements ConnectProvider
  {
    private EditorGraphMember<?> source = null;
    private EditorGraphMember<?> target = null;

    public boolean isSourceWidget (Widget sourceWidget)
    {
      Object object = findObject (sourceWidget);
      source = isNode (object) ? (EditorGraphMember<?>) object : null;
      return source != null;
    }

    public ConnectorState isTargetWidget (Widget sourceWidget, Widget targetWidget)
    {
      Object object = findObject (targetWidget);
      target = isNode (object) ? (EditorGraphMember<?>) object : null;
      if (target != null)
      {
        return ConnectorState.ACCEPT;
      }
      return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomTargetWidgetResolver (Scene scene)
    {
      return false;
    }

    public Widget resolveTargetWidget (Scene scene, Point sceneLocation)
    {
      return null;
    }

    public void createConnection (Widget sourceWidget, Widget targetWidget)
    {
      CommandStack.addArc( EditorScene.this, new EditorArc( source, target ) );
    }
  }

  public class SceneReconnectProvider implements ReconnectProvider
  {
    private EditorArc arc;
    private EditorGraphMember<?> originalNode;
    private EditorGraphMember<?> replacementNode;

    public void reconnectingStarted (ConnectionWidget connectionWidget, boolean reconnectingSource)
    {
      // does nothing
    }

    public void reconnectingFinished (ConnectionWidget connectionWidget, boolean reconnectingSource)
    {
      // does nothing
    }

    public boolean isSourceReconnectable (ConnectionWidget connectionWidget)
    {
      Object object = findObject (connectionWidget);
      arc = isEdge (object) ? (EditorArc) object : null;
      originalNode = arc != null ? getEdgeSource (arc) : null;
      return originalNode != null;
    }

    public boolean isTargetReconnectable (ConnectionWidget connectionWidget)
    {
      Object object = findObject (connectionWidget);
      arc = isEdge (object) ? (EditorArc) object : null;
      originalNode = arc != null ? getEdgeTarget (arc) : null;
      return originalNode != null;
    }

    public ConnectorState isReplacementWidget (ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource)
    {
      Object object = findObject (replacementWidget);
      replacementNode = isNode (object) ? (EditorGraphMember<?>) object : null;
      if (replacementNode != null)
      {
        return ConnectorState.ACCEPT;
      }
      return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
    }

    public boolean hasCustomReplacementWidgetResolver (Scene scene)
    {
        return false;
    }

    public Widget resolveReplacementWidget (Scene scene, Point sceneLocation)
    {
        return null;
    }

    public void reconnect (ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource)
    {
      if (replacementWidget == null)
      {
        CommandStack.deleteArc( EditorScene.this, arc );
      }
      else if (reconnectingSource)
      {
        CommandStack.updateArc( EditorScene.this, arc, true, replacementNode );
      }
      else
      {
        CommandStack.updateArc( EditorScene.this, arc, false, replacementNode );
      }
    }
  }
}