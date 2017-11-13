package entities;

public class MapEdge {
    private MapNode start, end;
    private String startID, endID;
    private String id;
    private double weight = -1;

    /**
     * Creates an edge on the graph, the weight is assumed to be the euclidean distance between the start and end nodes.
     * @param id
     * @param start
     * @param end
     */
    public MapEdge(String id, String start, String end) {
        this.startID = start;
        this.endID = end;
        this.id = id;
    }

    /**
     * Creates an edge using a defined weight
     * @param id
     * @param start
     * @param end
     * @param weight
     */
    public MapEdge(String id, String start, String end, double weight) {
        this.startID = start;
        this.endID = end;
        this.id = id;
        this.weight = weight;
    }

    /**
     * Calculates the weight of the edge using the distance formula
     * @return
     */
    private double calculateWeight() {
        return Math.sqrt(Math.pow(getEnd().getCoordinate().getxCoord()-getStart().getCoordinate().getxCoord(), 2)+
                Math.pow(getEnd().getCoordinate().getyCoord()-getStart().getCoordinate().getyCoord(), 2));
    }

    public MapNode getStart() {
        if(start == null) {
            start = HospitalMap.getInstance().getMap().getNode(startID);
        }
        return start;
    }

    public void setStart(MapNode start) {
        if(start == null) {
            System.out.println("Tried to set a null start");
            return;
        }
        this.start = start;
        if(end != null) {
            weight = calculateWeight();
        }
    }

    public MapNode getEnd() {
        if(end == null) {
            end = HospitalMap.getInstance().getMap().getNode(endID);
        }
        return end;
    }

    public void setEnd(MapNode end) {
        if(end == null) {
            System.out.println("Tried to set a null end");
            return;
        }
        this.end = end;
        if(start != null) {
            weight = calculateWeight();
        }
    }

    public double getWeight() {
        if(weight == -1) {
            weight = calculateWeight();
        }
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String toCSV() {
        return String.format("\"%s\",\"%s\",\"%s\"", getId(), start.getId(), end.getId());
    }

    public String toSQLVals() {
        return String.format("'%s','%s','%s'", getId(), start.getId(), end.getId());
    }
}
