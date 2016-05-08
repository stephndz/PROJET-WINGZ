/*
 * Copyright (c) 2016. The Wingz Project
 * Developed by Wingz Team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wingz.core.model;

/**
 * Created by Dasha on 10/04/16.
 */
public class Destination {


    private long id;
    private String city;
    private String public_transport;
    private String private_transport;
    private String hotel;
    private String restaurant;
    private String events;


    public Destination(){

    }
    public Destination(long id, String city, String public_transport, String private_transport, String hotel, String restaurant, String events) {
        this.id = id;
        this.city = city;
        this.public_transport = public_transport;
        this.private_transport = private_transport;
        this.hotel = hotel;
        this.restaurant = restaurant;
        this.events = events;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPublic_transport() {
        return public_transport;
    }

    public void setPublic_transport(String public_transport) {
        this.public_transport = public_transport;
    }

    public String getPrivate_transport() {
        return private_transport;
    }

    public void setPrivate_transport(String private_transport) {
        this.private_transport = private_transport;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }
}
