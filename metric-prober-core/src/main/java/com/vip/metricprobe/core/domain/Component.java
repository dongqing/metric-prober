package com.vip.metricprobe.core.domain;


/**
 * 业务组件，
 * Created by dongqingswt on 14-11-17.
 */
public class Component {
    /**
     * 名称
     */
    private String name ;

    /**
     * 负责人
     */
    private String owner ;

    /**
     * 描述
     */
    private String description;


    /**
     * 系统分配的id ;
     */
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object obj){
        if( obj == null || ! (obj instanceof  Component)){
            return false;
        }

        Component c = (Component) obj;
        return  c.id != null && c.id.equals(this.id);
    }

    @Override
    public int hashCode(){

        return this.id == null ? 0 : this.id.hashCode() ;
    }

    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append(" component:[").append("name:").append(this.name)
                .append(",owner:").append(this.owner)
                .append(",id:").append(this.id).append("] ") ;
        return s.toString();
    }
}
