package BT_20_12.Management.Entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "tasks", schema = "crm_app", catalog = "")
public class TasksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "start_date")
    private String startDate;
    @Basic
    @Column(name = "end_date")
    private String endDate;
    @JoinColumn(name = "user")
    @ManyToOne
    private UsersEntity userId;
    @JoinColumn(name = "job")
    @ManyToOne
    private JobsEntity job;
    @JoinColumn(name = "status")
    @ManyToOne
    private StatusEntity status;

    public TasksEntity(String name,String date_start,String date_end,JobsEntity job,UsersEntity user,StatusEntity status){
        this.name = name;
        this.startDate = date_start;
        this.endDate = date_end;
        this.job = job;
        this.userId = user;
        this.status = status;
    }

    public void setUserId(UsersEntity userId) {
        this.userId = userId;
    }

    public TasksEntity() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public JobsEntity getJob() {
        return job;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public UsersEntity getUserId() {
        return userId;
    }

    public void setJob(JobsEntity job) {
        this.job = job;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TasksEntity that = (TasksEntity) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (job != that.job) return false;
        if (status != that.status) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }
}
