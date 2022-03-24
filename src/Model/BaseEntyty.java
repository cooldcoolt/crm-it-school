package Model;

import java.time.LocalDateTime;

abstract class BaseEntyty {
protected Long id;
protected LocalDateTime date_created;

    public BaseEntyty(Long id) {
        this.id = id;
        this.date_created = LocalDateTime.now();
    }
protected BaseEntyty(){
        this.date_created = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
public void setId(Long id){
        this.id = id;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }
}

