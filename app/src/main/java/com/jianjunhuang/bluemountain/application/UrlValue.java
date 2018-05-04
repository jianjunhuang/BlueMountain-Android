package com.jianjunhuang.bluemountain.application;

public class UrlValue {

    public static final String BASE_URL = "http://10.11.3.213:8080/";
    public static final String REGISTER = BASE_URL + "user/register";
    public static final String LOGIN = BASE_URL + "user/login";
    public static final String CHECK_USERNAME = BASE_URL + "user/checkUserName";
    public static final String USER_UPDATE = BASE_URL + "user/update";
    public static final String CONNECT = BASE_URL + "user/connect";
    public static final String DISCONNECT = BASE_URL + "user/disconnected";
    public static final String UPDATE_USER_NAME = BASE_URL + "user/updateUserName";
    public static final String UPDATE_USER_CUP_SIZE = BASE_URL + "user/updateUserCupSize";

    public static final String GET_COMMUNITY = BASE_URL + "community/get";
    public static final String ADD_COMMUNITY = BASE_URL + "community/addAVote";
    public static final String VOTE_COMMUNITY = BASE_URL + "community/vote";

    public static final String GET_ALL_USERS = BASE_URL + "user/getAllUsers";

    public static final String GET_MACHINE = BASE_URL + "machine/getMachine";
    public static final String UPDATE_MACHINE_INSULATION = BASE_URL + "machine/setInsulationTemperature";

    public static final String ORDER_COFFEE = BASE_URL + "machine/orderCoffee";
}
