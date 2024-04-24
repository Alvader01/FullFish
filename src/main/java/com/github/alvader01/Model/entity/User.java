package com.github.alvader01.Model.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User {

    private String username;
    private String name;
    private String password;
    private String email;
    private List<FishTank> fishTanks;

    public User(String username, String name, String password, String email, List<FishTank> fishTanks) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.fishTanks = fishTanks;
    }

    public User() {
        username = "";
        name = "";
        password = "";
        email = "";
        fishTanks = null;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<FishTank> getFishTanks() {
        return fishTanks;
    }

    public void setFishTanks(List<FishTank> fishTanks) {
        this.fishTanks = fishTanks;
    }

    public void addFishTank(FishTank fishtank){
        if(fishTanks ==null){
            fishTanks = new ArrayList<>();
        }
        if(!fishTanks.contains(fishtank)){
            fishTanks.add(fishtank);
        }
    }
    public void removeFishTank(FishTank fishtank){
        if(fishTanks !=null){
            fishTanks.remove(fishtank);
        }
    }

    public FishTank getFishTank(FishTank fishtank){
        FishTank result=null;
        if(fishTanks !=null){
            int i= fishTanks.indexOf(fishtank);
            result = fishTanks.get(i);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

