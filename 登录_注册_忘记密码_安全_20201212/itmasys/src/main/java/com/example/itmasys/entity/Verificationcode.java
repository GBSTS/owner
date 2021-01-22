package com.example.itmasys.entity;


public class Verificationcode {
    private Integer id;
    private String email;
    private Integer code;
    private Integer createdate;

    public Verificationcode() {
    }

    public Verificationcode(String email, Integer code, Integer createdate) {
        this.email = email;
        this.code = code;
        this.createdate = createdate;
    }

    public Verificationcode(Integer id, String email, Integer code, Integer createdate) {
        this.id = id;
        this.email = email;
        this.code = code;
        this.createdate = createdate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return this.id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setCreatedate(Integer createdate) {
        this.createdate = createdate;
    }

    public Integer getCreatedate() {
        return this.createdate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) {return false;}
        Verificationcode that = (Verificationcode) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Verificationcode{" +
                "id=" + id +
                ",email='" + email + "'" +
                ",code='" + code + "'" +
                ",createdate='" + createdate + "'" +
                '}';
    }

}