package com.pak.villagerwardrobe.screen.custom;

import com.google.gson.JsonObject;

public record Outfit(String id, String displayName, String texture) {

  public static Outfit fromJson(JsonObject obj) {
    String id = obj.get("id").getAsString();
    String displayName = obj.get("display_name").getAsString();
    String texture = obj.get("texture").getAsString();
    return new Outfit(id, displayName, texture);
  }
}