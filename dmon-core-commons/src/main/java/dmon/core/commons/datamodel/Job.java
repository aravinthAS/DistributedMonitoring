package dmon.core.commons.datamodel;

/**
 * Job that will be passed through the queues.
 */
public class Job {
    /**
     * The ID where data can be found in DB
     */
    private String id;

    public Job() {}

    public Job(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
