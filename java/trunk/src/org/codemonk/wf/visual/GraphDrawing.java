package org.codemonk.wf.visual;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import org.codemonk.wf.Arc;
import org.codemonk.wf.db.HibGraph;
import org.codemonk.wf.db.HibNodeRef;

public class GraphDrawing extends JComponent
{
  private static final long serialVersionUID = 1L;

  protected HibGraph graph = null;
  protected GraphTree graphTree = null;

  public GraphDrawing ()
  {
  }

  public HibGraph getGraph ()
  {
    return graph;
  }

  public void setGraph (HibGraph graph)
  {
    this.graph = graph;
    this.graphTree = graph == null ? null : new GraphTree( graph );

    if ( graphTree != null )
    {
      int maxHeight = 0;

      for ( List<?> layer : graphTree.getLayers() )
      {
        if ( layer.size() > maxHeight )
        {
          maxHeight = layer.size();
        }
      }

      int nodeSize = NodeDrawConfig.getMaxNodeRadius() << 1;
      int spaceSize = NodeDrawConfig.getNodeSpacing();

      int width  = graphTree.getLayerCount() * nodeSize + (graphTree.getLayerCount() + 1) * spaceSize;

      int height = maxHeight * nodeSize + (maxHeight + 1) * spaceSize;

      setSize( width, height );
    }
  }

  @Override
  public void paintComponent( Graphics g )
  {
    super.paintComponent( g );

    if ( graphTree == null || graphTree.getLayerCount() == 0)
    {
      return;
    }

    g.setColor( Color.white );
    g.fillRect( 0, 0, getWidth(), getHeight() );
    g.setColor( Color.black );

    for ( int x = 0; x < graphTree.getLayerCount(); x++ )
    {
      List<GraphTreeNode> layer = graphTree.getLayer( x );

      for ( int y = 0; y < layer.size(); y++ )
      {
        GraphTreeNode treeNode = layer.get( y );
        treeNode.paintNode( g );

        for ( Arc arc : graph.getOutputArcs( treeNode.getNode() ) )
        {
          GraphTreeNode targetNode = graphTree.getTreeNode( (HibNodeRef)arc.getEndNode() );
          drawArc( g, arc, treeNode, targetNode );
        }
      }
    }
  }

  /**
   *   --------------
   *  /              \
   * ***      ***    ***
   * * * ---> * * -> * *
   * *** \    ***    ***
   *      \
   *       \  ***
   *        > * *
   *          ***
   * @param g
   * @param arc
   * @param start
   * @param end
   */
  protected void drawArc (Graphics g, Arc arc, GraphTreeNode start, GraphTreeNode end)
  {
    boolean isReject = "reject".equals( arc.getName() );
    g.setColor( isReject ? Color.red : Color.black );

    if ( end.getDepth() - start.getDepth() == 1 )
    {
      Point startPoint = start.getRightAnchor();
      Point endPoint   = end.getLeftAnchor();

      if ( graph.hasArcInverse( arc ) )
      {
        g.drawArc( startPoint.x, startPoint.y, 1,1,1,1 );
      }
      else
      {
        g.drawLine( startPoint.x, startPoint.y, endPoint.x, endPoint.y );
        end.paintLeftIncomingAnchor( g );
      }
    }
  }

  @Override
  public Dimension getPreferredSize ()
  {
    return getSize();
  }
}