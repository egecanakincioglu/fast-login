package com.cartel.fastlogin.database;

public class playerData {
    private String playerUUID;
    private String playerIP;

    public playerData(String playerUUID, String playerIP) {
        this.playerUUID = playerUUID;
        this.playerIP = playerIP;
    }

    public String getPlayerUUID() {
        return playerUUID;
    }

    public String getPlayerIP() {
        return playerIP;
    }
}
