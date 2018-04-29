package com.jianjunhuang.bluemountain.model;

import com.jianjunhuang.bluemountain.contact.CoffeeContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CoffeeModelTest {


    @Test
    public void getUsers() {

        CoffeeModel model = new CoffeeModel();
        model.setCallback(new CoffeeContact.Callback() {
            @Override
            public void onGetUsersSuccess(List<User> users) {
                System.out.println(users);
            }

            @Override
            public void onGetUsersFailed(String reason) {
                System.out.println(reason);
            }

            @Override
            public void onGetMachineSuccess(Machine machine) {

            }

            @Override
            public void onGetMachineFailed(String reason) {

            }
        });
        model.getUsers("ff80818162fcc0690162fcc0989a0000", "123465");

    }
}