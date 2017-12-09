package com.teama.mapsubsystem.pathfinding.DijkstrasFamily.AStar;
import com.teama.mapsubsystem.data.MapNode;
import com.teama.mapsubsystem.data.MapEdge;
import com.teama.mapsubsystem.pathfinding.Path;
import com.teama.mapsubsystem.pathfinding.DijkstrasFamily.Dijkstras.Dijkstras;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class AStar extends Dijkstras {


    protected HashMap<String,KnownPointA> checkedPoints;
    private PriorityQueue<KnownPointA> queue;
    protected MapNode start, end;
    private HashMap<String, MapNode> disableNodes;


    /**
     * This is the a star itself.
     * @param start the start MapNode
     * @param end the end MapNode
     * @return the path generated by a star
     */
    @Override
    public Path generatePath(MapNode start, MapNode end) {
        this.start=start;
        this.end=end;
        checkedPoints= new HashMap<>();
        queue=new PriorityQueue<>();


        KnownPointA checking ; // create a temp variable to keep track of which node are we on.

        //Generate Path
        for(checking = new KnownPointA(start,null,0,calDistance(start,end));
            !checking.getNode().getId().equals(end.getId());   // reached end
            checking=queue.poll() // move forward one step
                )
        {
            putNodesIntoQueue(checking); // put adjacent node into queue.
            checkedPoints.put(checking.getNode().getId(),checking);
            if(queue.peek()==null) {
                throw new java.lang.RuntimeException("Cannot generate a route from the given start and end.");
            }
            // @TODO double check if this is good enough for errors.
        }
        // Done generating, output the path
        // make it into the format of outputting.
        return formatOutput(collectPath(checking));
    }

    //TODO fill this function
    @Override
    public Path generatePath(MapNode start, MapNode end, ArrayList<MapNode> disableNodes){
        this.disableNodes = new HashMap<String, MapNode>();
        grabDisableNodes(disableNodes);
        return generatePath(start, end);
    }

    ////////////////////// helper ///////////////////////


    /**
     * This helper function is to use the abs value of coordinates difference to calculate difference.
     * @param n1 is the start node.
     * @param n2 is the end node.
     * @return returns the sum of x coord diff and y coord diff.
     */
    protected int calDistance(MapNode n1, MapNode n2)
    {
        double x = (double) abs(n1.getCoordinate().getxCoord() - n2.getCoordinate().getxCoord());
        double y = (double) abs(n1.getCoordinate().getyCoord() - n2.getCoordinate().getxCoord());
        return (int) sqrt ( x*x+y*y ) ;
    }



    /**
     * This helper function is to put all the nodes that are linked to checking into the queue.
     * @param checking is the node currently under examining.
     */
    protected void putNodesIntoQueue (KnownPointA checking)
    {
        for(MapEdge e : checking.getEdge()) // putting the adjacentNodes into queue
        {
            MapNode nextNode= adjacentNode(e,checking.getNode());  // get the node to be calculated.
            if(disableNodes.containsKey(nextNode.getId())) continue; //skip this node if it is in the disabled list
            if( !checkedPoints.containsKey(nextNode.getId())) {  // prevent from going to points already been at.
                int newPastCost = checking.getPastCost() + (int) e.getWeight();

                KnownPointA nextPoint = new KnownPointA(nextNode, checking, newPastCost,
                        newPastCost + calDistance(nextNode, end)); // Generate a new Point from checking point to add into queue.
                queue.add(nextPoint); // add into queue
            }
        }
    }


    /**
     * This helper function is to generate the Path from end point and put them in a list.
     * @param lastPoint the end point the Path
     * @return  the reversed Path
     */
    protected ArrayList<MapNode> collectPath(KnownPointA lastPoint)
    {
        ArrayList<MapNode> finalPath = new ArrayList<>();
        for (;lastPoint.getLastNode()!=null;)
        {
            finalPath.add(lastPoint.getNode());
            lastPoint=lastPoint.getLastNode();
        }
        finalPath.add(lastPoint.getNode());
        return finalPath;
    }


}
