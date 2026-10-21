package com.example.locker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Locker{

    @Id
    private String number;
    private String location;

    @Enumerated(EnumType.STRING)
    private LockerSize size;

    @Enumerated(EnumType.STRING)
    private LockerStatus status;

    // 양방향 연관관계 굳이 필요할까?
    // 요구사항에 storage 리스트의 사용이 필요한것이 있나?
    @OneToMany(mappedBy = "locker")
    private List<Storage> storage = new ArrayList<>();

    public Locker(
        String number,
        String location,
        LockerSize size
    ){
        this.number = number;
        this.location = location;
        this.size = size;
        this.status = LockerStatus.AVAILABLE;
    }

    protected Locker(){
    }

    public String getNumber(){
        return number;
    }

    public String getLocation(){
        return location;
    }

    public LockerSize getSize(){
        return size;
    }

    public LockerStatus getStatus(){
        return status;
    }

    public void updateStatusOccupied(){
        this.status = LockerStatus.OCCUPIED;
    }

    public void updateStatusAvailable(){
        this.status = LockerStatus.AVAILABLE;
    }

    public void occupy(){
        if(!(this.status == LockerStatus.AVAILABLE)){
            // Exception throw.
            // 차후 구현
        }

        updateStatusOccupied();
    }

    public void terminate(){
        if(!(this.status == LockerStatus.OCCUPIED)){
            // Exception throw;
            // 차후 구현
        }
        
        updateStatusAvailable();
    }
}