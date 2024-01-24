package dao;

public class ConstantsDao {

    /*Customers*/
    public static final String ERROR_READING_DATABASE = "Error when reading from the database";
    public static final String ID = "id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String ERROR_DELETING_CUSTOMER = "Error when deleting the customer";
    public static final String ERROR_OBTAINING_ID = "There was a problem obtaining the ID";
    public static final String ERROR_ADDDING_CUSTOMER = "There was a problem addding the customer";

    /*Orders*/

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_DATE = "order_date";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String TABLE_ID = "table_id";
    public static final String ERROR_DELETING_ORDER = "Error when deleting the order";
    public static final String ERROR_ADDING_ORDER = "There was a problem adding the order";

    /*Order Items*/

    public static final String ORDER_ITEM_ID = "order_item_id";
    public static final String MENU_ITEM_ID = "menu_item_id";
    public static final String QUANTITY = "quantity";

    /*Tables*/

    public static final String TABLE_NUMBER_ID = "table_number_id";
    public static final String NUMBER_OF_SEATS = "number_of_seats";

    /*Menu Items*/


    /*Connection /// Connection Pool*/

    public static final String PATH_DB = "pathDB";
    public static final String USER_DB = "userDB";
    public static final String PASS_DB = "passDB";
    public static final String DRIVER = "driver";
    public static final String ERROR_ACCESSING_DB = "There was an error accessing the database";
    public static final String ROOT = "data/customer_";
    public static final String XML = ".xml";
}
