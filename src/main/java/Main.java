import db2.*;
import db2.mappers.*;
import org.hibernate.type.descriptor.jdbc.JsonJdbcType;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    static String[] cargoTypeTable = {"Id", "Cargo"};
    static String[] carsTable = {"Id", "Brand", "Number", "HigherCapacity", "CargoType", "Condition"};
    static String[] clientsTable = {"Id", "Surname", "PhoneNumber"};
    static String[] conditionTable = {"Id", "Condition"};
    static String[] driversTable = {"Id", "Category", "Experience"};
    static String[] movingInfoTable = {"Id", "Position", "OrderNumber", "OrderDate"};
    static String[] ordersTable = {"Id", "SubmissionAddress", "DestinationAddress", "HigherPrice", "Date", "DateOrderExecution"};
    static String[] stuffTable = {"Id", "Surname", "Address", "Birthday", "Position", "HigherSalary"};
    public static void CargoTypeMenu(){
        CargoTypeMapper cargoTypeMapper = new CargoTypeMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add cargo type");
            System.out.println("4. Delete cargo type");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<CargoType> cargoTypeList = cargoTypeMapper.findAll();
                    System.out.println(CargoType.toString(cargoTypeList));

                    break;
                }
                case 2: // поиск
                {
                    System.out.println("Search by:");
                    for (int i = 0; i < cargoTypeTable.length; i++){
                        System.out.println((i + 1) + ". " + cargoTypeTable[i]);
                    }

                    try{
                        num = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    if (num < 1 || num > cargoTypeTable.length)
                    {
                        System.out.println("Invalid value");
                        break;
                    }

                    System.out.println("Enter searching value");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    List<CargoType> result =  cargoTypeMapper.findByField(".by" + cargoTypeTable[num - 1], choice);
                    System.out.println(CargoType.toString(result));
                    break;
                }
                case 3:
                {
                    CargoType cargoType = new CargoType();
                    System.out.println("Enter cargo type");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    cargoType.setCargo(choice);
                    cargoTypeMapper.save(cargoType);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter cargo type id");
                    int choice = in.nextInt();
                    CargoType cargoType = cargoTypeMapper.findByField(".byId", choice).get(0);
                    CarsMapper carsMapper = new CarsMapper();
                    boolean flag = false;
                    List<Cars> carsList= carsMapper.findAll();
                    for (int i = 0; i < carsList.size(); i++){
                        if  (carsList.get(i).getCargoTypeByCargoType().getId() == cargoType.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else cargoTypeMapper.delete(cargoType);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter cargo type id");
                    int number = in.nextInt();
                    CargoType cargoType = cargoTypeMapper.findByField(".byId", number).get(0);
                    System.out.println("Enter cargo type");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    cargoType.setCargo(choice);
                    cargoTypeMapper.edit(cargoType);
                    break;
                }
                case 0:
                {
                    return;
                }
            }
        }
    }

    public static void CarsMenu(){
        CarsMapper carsMapper = new CarsMapper();
        DriversMapper driversMapper = new DriversMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add car");
            System.out.println("4. Delete car");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Cars> carsList = carsMapper.findAll();
                    System.out.println(Cars.toString(carsList));
                    break;
                }
                case 2:
                {
                    System.out.println("Search by:");
                    for (int i = 0; i < carsTable.length; i++){
                        System.out.println((i + 1) + ". " + carsTable[i]);
                    }

                    System.out.println((carsTable.length + 1) + ". Driver by car id");

                    try{
                        num = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < carsTable.length)
                    {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Cars> result =  carsMapper.findByField(".by" + carsTable[num - 1], choice);
                        System.out.println(Cars.toString(result));
                        break;
                    }

                    if (num == carsTable.length + 1){
                        System.out.println("Enter car id");
                        try{
                            num = in.nextInt();
                        } catch (InputMismatchException e){
                            throw new RuntimeException(e);
                        }

                        List<Drivers> resultList = driversMapper.findAll();
                        for (int i = 0; i < resultList.size(); i++)
                            if (resultList.get(i).getCarsByCar().getId() == num)
                                System.out.println(resultList.get(i));
                        break;
                    }

                    break;
                }
                case 3:
                {
                    Cars cars = new Cars();
                    System.out.println("Enter brand");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    cars.setBrand(choice);

                    System.out.println("Enter number");
                    int number = in.nextInt();
                    cars.setNumber(number);

                    System.out.println("Enter load capacity");
                    Double load_capacity = in.nextDouble();
                    cars.setLoadCapacity(load_capacity);

                    CargoTypeMapper cargoTypeMapper = new CargoTypeMapper();
                    System.out.println(cargoTypeMapper.findAll());

                    System.out.println("Enter cargo type id");
                    number = in.nextInt();
                    CargoType cargo = cargoTypeMapper.findByField(".byId", number).get(0);
                    cars.setCargoTypeByCargoType(cargo);

                    ConditionMapper conditionMapper = new ConditionMapper();
                    System.out.println(conditionMapper.findAll());

                    System.out.println("Enter condition id");
                    number = in.nextInt();
                    Condition condition = conditionMapper.findByField(".byId", number).get(0);
                    cars.setConditionByCondition(condition);

                    carsMapper.save(cars);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter condition id");
                    int choice = in.nextInt();
                    Cars cars = carsMapper.findByField(".byId", choice).get(0);
                    boolean flag = false;
                    List<Drivers> driversList = driversMapper.findAll();
                    for (int i = 0; i < driversList.size(); i++){
                        if  (driversList.get(i).getCarsByCar().getId() == cars.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else carsMapper.delete(cars);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter car id");
                    int number = in.nextInt();
                    Cars cars = carsMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Brand");
                    System.out.println("2. Number");
                    System.out.println("3. Load capacity");
                    System.out.println("4. Cargo type id");
                    System.out.println("5. Condition id");

                    try{
                        num = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    switch (num){
                        case 1:
                        {
                            System.out.println("Enter brand");
                            String choice = in.nextLine();
                            choice = in.nextLine();
                            cars.setBrand(choice);
                            break;
                        }
                        case 2:
                        {
                            System.out.println("Enter number");
                            number = in.nextInt();
                            cars.setNumber(number);
                            break;
                        }
                        case 3:
                        {
                            System.out.println("Enter load capacity");
                            Double load_capacity = in.nextDouble();
                            cars.setLoadCapacity(load_capacity);
                        }
                        case 4:
                        {
                            CargoTypeMapper cargoTypeMapper = new CargoTypeMapper();
                            System.out.println(CargoType.toString(cargoTypeMapper.findAll()));

                            System.out.println("Enter cargo type id");
                            number = in.nextInt();
                            CargoType cargo = cargoTypeMapper.findByField(".byId", number).get(0);
                            cars.setCargoTypeByCargoType(cargo);
                        }
                        case 5:
                        {
                            ConditionMapper conditionMapper = new ConditionMapper();
                            System.out.println(Condition.toString(conditionMapper.findAll()));

                            System.out.println("Enter condition id");
                            number = in.nextInt();
                            Condition condition = conditionMapper.findByField(".byId", number).get(0);
                            cars.setConditionByCondition(condition);
                        }
                    }

                    carsMapper.edit(cars);
                    break;
                }
                case 0:
                {
                    return;
                }

            }

        }
    }

    public static void ClientsMenu(){
        ClientsMapper clientsMapper = new ClientsMapper();
        OrdersMapper ordersMapper = new OrdersMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add client type");
            System.out.println("4. Delete client type");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Clients> clientsList = clientsMapper.findAll();
                    System.out.println(Clients.toString(clientsList));
                    break;
                }
                case 2:
                {
                    System.out.println("Search by:");
                    for (int i = 0; i < clientsTable.length; i++){
                        System.out.println((i + 1) + ". " + clientsTable[i]);
                    }

                    System.out.println("4. Order info by client id");

                    try{
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < clientsTable.length)
                    {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Clients> result =  clientsMapper.findByField(".by" + clientsTable[num], choice);
                        System.out.println(Clients.toString(result));
                        break;
                    }

                    if (num == 3){
                        System.out.println("Enter client id");
                        try{
                            num = in.nextInt();
                        } catch (InputMismatchException e){
                            throw new RuntimeException(e);
                        }

                        List<Orders> resultList = ordersMapper.findAll();
                        for (int i = 0; i < resultList.size(); i++)
                            if (resultList.get(i).getClientsByClient().getId() != num)
                                System.out.println(resultList.get(i));
                        break;
                    }
                    break;
                }
                case 3:
                {
                    String choice;
                    int number;
                    Clients clients = new Clients();
                    System.out.println("Enter surname");
                    choice = in.nextLine();
                    choice = in.nextLine();
                    clients.setSurname(choice);

                    System.out.println("Enter name");
                    choice = in.nextLine();
                    clients.setName(choice);

                    System.out.println("Enter middle name");
                    choice = in.nextLine();
                    clients.setMiddleName(choice);

                    System.out.println("Enter phone number");
                    choice = in.nextLine();
                    clients.setPhoneNumber(choice);

                    clientsMapper.save(clients);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter client id");
                    int choice = in.nextInt();
                    Clients clients = clientsMapper.findByField(".byId", choice).get(0);
                    boolean flag = false;
                    List<Orders> ordersList = ordersMapper.findAll();
                    for (int i = 0; i < ordersList.size(); i++){
                        if  (ordersList.get(i).getClientsByClient().getId() == clients.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else clientsMapper.delete(clients);

                    break;
                }
                case 5: {
                    System.out.println("Enter client id");
                    int number = in.nextInt();
                    Clients clients = clientsMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Surname");
                    System.out.println("2. Name");
                    System.out.println("3. Middle name");
                    System.out.println("4. Phone number");

                    try{
                        number = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    String choice;

                    switch (number){
                        case 1:{
                            System.out.println("Enter surname");
                            choice = in.nextLine();
                            choice = in.nextLine();
                            clients.setSurname(choice);
                            break;
                        }
                        case 2:{
                            System.out.println("Enter name");
                            choice = in.nextLine();
                            clients.setName(choice);
                            break;
                        }
                        case 3:{
                            System.out.println("Enter middle name");
                            choice = in.nextLine();
                            clients.setMiddleName(choice);
                            break;
                        }
                        case 4:{
                            System.out.println("Enter phone number");
                            choice = in.nextLine();
                            clients.setPhoneNumber(choice);
                            break;
                        }
                    }
                    clientsMapper.edit(clients);

                }
                case 0:
                {
                    return;
                }
            }

        }
    }

    public static void ConditionMenu(){
        ConditionMapper conditionMapper = new ConditionMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add condition");
            System.out.println("4. Delete condition");
            System.out.println("5. Edit");
            System.out.println("0. Exit");
            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Condition> conditionList = conditionMapper.findAll();
                    System.out.println(Condition.toString(conditionList));
                    break;
                }
                case 2: {
                    System.out.println("Search by:");
                    for (int i = 0; i < conditionTable.length; i++) {
                        System.out.println((i + 1) + ". " + conditionTable[i]);
                    }

                    try {
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < conditionTable.length) {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Condition> result = conditionMapper.findByField(".by" + conditionTable[num], choice);
                        System.out.println(Condition.toString(result));
                        break;
                    }

                    break;
                }
                case 3:
                {
                    Condition condition = new Condition();
                    System.out.println("Enter condition");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    condition.setCondition(choice);
                    conditionMapper.save(condition);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter condition id");
                    int choice = in.nextInt();
                    Condition condition = conditionMapper.findByField(".byId", choice).get(0);
                    CarsMapper carsMapper = new CarsMapper();
                    boolean flag = false;
                    List<Cars> carsList = carsMapper.findAll();
                    for (int i = 0; i < carsList.size(); i++){
                        if  (carsList.get(i).getConditionByCondition().getId() == condition.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else conditionMapper.delete(condition);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter condition id");
                    int number = in.nextInt();
                    Condition condition = conditionMapper.findByField(".byId", number).get(0);
                    System.out.println("Enter condition");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    condition.setCondition(choice);
                    conditionMapper.edit(condition);
                    break;
                }
                case 0:
                {
                    return;
                }

            }

        }
    }

    public static void DriversMenu(){
        DriversMapper driversMapper = new DriversMapper();
        StuffMapper stuffMapper = new StuffMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add driver");
            System.out.println("4. Delete driver");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Drivers> driversList = driversMapper.findAll();
                    System.out.println(Drivers.toString(driversList));
                    break;
                }
                case 2: {
                    System.out.println("Search by:");
                    for (int i = 0; i < driversTable.length; i++) {
                        System.out.println((i + 1) + ". " + driversTable[i]);
                    }

                    System.out.println((carsTable.length + 1) + ". Stuff by driver id");

                    try {
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < driversTable.length) {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Drivers> result = driversMapper.findByField(".by" + driversTable[num], choice);
                        System.out.println(Drivers.toString(result));
                        break;
                    }

                    if (num == driversTable.length + 1){
                        System.out.println("Enter driver id");
                        try{
                            num = in.nextInt();
                        } catch (InputMismatchException e){
                            throw new RuntimeException(e);
                        }

                        List<Stuff> resultList = stuffMapper.findAll();
                        for (int i = 0; i < resultList.size(); i++)
                            if (resultList.get(i).getId() == num)
                                System.out.println(resultList.get(i));
                        break;
                    }

                    break;
                }
                case 3:
                {
                    Drivers driver = new Drivers();
                    System.out.println("Enter category");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    driver.setCategory(choice);

                    System.out.println("Enter experience");
                    int number = in.nextInt();
                    driver.setExperience(number);

                    CarsMapper carsMapper = new CarsMapper();
                    System.out.println(Cars.toString(carsMapper.findAll()));

                    System.out.println("Enter car id");
                    number = in.nextInt();
                    Cars car = carsMapper.findByField(".byId", number).get(0);
                    driver.setCarsByCar(car);

                    System.out.println(Stuff.toString(stuffMapper.findAll()));

                    System.out.println("Enter stuff id");
                    number = in.nextInt();
                    Stuff stuff = stuffMapper.findByField(".byId", number).get(0);
                    driver.setStuffByStuff(stuff);

                    driversMapper.save(driver);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter driver id");
                    int choice = in.nextInt();
                    Drivers drivers = driversMapper.findByField(".byId", choice).get(0);
                    OrdersMapper ordersMapper = new OrdersMapper();
                    boolean flag = false;
                    List<Orders> ordersList = ordersMapper.findAll();
                    for (int i = 0; i < ordersList.size(); i++){
                        if  (ordersList.get(i).getDriversByDriver().getId() == drivers.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else driversMapper.delete(drivers);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter driver id");
                    int number = in.nextInt();
                    Drivers driver = driversMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Category");
                    System.out.println("2. Experience");
                    System.out.println("3. Stuff id");
                    System.out.println("4. Car id");

                    try{
                        number = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    String choice;

                    switch (number){
                        case 1:{
                            System.out.println("Enter category");
                            choice = in.nextLine();
                            choice = in.nextLine();
                            driver.setCategory(choice);
                            break;
                        }
                        case 2:{
                            System.out.println("Enter experience");
                            number = in.nextInt();
                            driver.setExperience(number);
                            break;
                        }
                        case 3:{
                            CarsMapper carsMapper = new CarsMapper();
                            System.out.println(Cars.toString(carsMapper.findAll()));

                            System.out.println("Enter car id");
                            number = in.nextInt();
                            Cars car = carsMapper.findByField(".byId", number).get(0);
                            driver.setCarsByCar(car);
                            break;
                        }
                        case 4:{
                            //System.out.println(stuffMapper.findAll());
                            System.out.println(Stuff.toString(stuffMapper.findAll()));


                            System.out.println("Enter stuff id");
                            number = in.nextInt();
                            Stuff stuff = stuffMapper.findByField(".byId", number).get(0);
                            driver.setStuffByStuff(stuff);
                            break;
                        }
                    }
                    driversMapper.edit(driver);

                    break;
                }
                case 0:
                {
                    return;
                }

            }

        }
    }
    public static void MovingInfoMenu() throws ParseException {
        MovingInfoMapper movingInfoMapper = new MovingInfoMapper();
        StuffMapper stuffMapper = new StuffMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add moving information");
            System.out.println("4. Delete moving information");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<MovingInfo> movingInfoList = movingInfoMapper.findAll();
                    System.out.println(MovingInfo.toString(movingInfoList));
                    break;
                }
                case 2: {
                    System.out.println("Search by:");
                    for (int i = 0; i < movingInfoTable.length; i++) {
                        System.out.println((i + 1) + ". " + movingInfoTable[i]);
                    }

                    System.out.println((movingInfoTable.length + 1) + ". Stuff by moving info id");

                    try {
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < movingInfoTable.length) {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<MovingInfo> result = movingInfoMapper.findByField(".by" + movingInfoTable[num], choice);
                        System.out.println(MovingInfo.toString(result));
                        break;
                    }

                    if (num == movingInfoTable.length){
                        System.out.println("Enter moving info id");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        MovingInfo result = movingInfoMapper.findByField(".byId", choice).get(0);
                        List<Stuff> resultList = stuffMapper.findByField(".byId", result.getStuffByStuffId().getId());
                        System.out.println(Stuff.toString(resultList));
                    }

                    break;
                }
                case 3:
                {
                    MovingInfo movingInfo = new MovingInfo();
                    System.out.println("Enter position");
                    String choice = in.nextLine();
                    choice = in.nextLine();
                    movingInfo.setPosition(choice);

                    System.out.println("Enter reasons for transfer");
                    choice = in.nextLine();
                    choice = in.nextLine();
                    movingInfo.setReasonsForTransfer(choice);

                    System.out.println("Enter order number");
                    int number = in.nextInt();
                    movingInfo.setOrderNumber(number);

                    System.out.println("Enter order date");
                    choice = in.nextLine();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    java.util.Date date = formatter.parse(choice);
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    movingInfo.setOrderDate(sqlDate);

                    System.out.println(Stuff.toString(stuffMapper.findAll()));

                    System.out.println("Enter stuff id");
                    number = in.nextInt();
                    Stuff stuff = stuffMapper.findByField(".byId", number).get(0);
                    movingInfo.setStuffByStuffId(stuff);

                    movingInfoMapper.save(movingInfo);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter moving information id");
                    int choice = in.nextInt();
                    MovingInfo movingInfo = movingInfoMapper.findByField(".byId", choice).get(0);
                    movingInfoMapper.delete(movingInfo);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter moving info id");
                    int number = in.nextInt();

                    MovingInfo movingInfo = movingInfoMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Position");
                    System.out.println("2. Reasons for transfer");
                    System.out.println("3. Order number");
                    System.out.println("4. Stuff id");

                    try{
                        number = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    String choice;

                    switch (number){
                        case 1:{
                            System.out.println("Enter position");
                            choice = in.nextLine();
                            choice = in.nextLine();
                            movingInfo.setPosition(choice);
                            break;
                        }
                        case 2:{
                            System.out.println("Enter reasons for transfer");
                            choice = in.nextLine();
                            movingInfo.setReasonsForTransfer(choice);
                            break;
                        }
                        case 3:{
                            System.out.println("Enter order date");
                            choice = in.nextLine();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            java.util.Date date = formatter.parse(choice);
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            movingInfo.setOrderDate(sqlDate);
                            break;
                        }
                        case 4:{
                            System.out.println(Stuff.toString(stuffMapper.findAll()));

                            System.out.println("Enter stuff id");
                            number = in.nextInt();
                            Stuff stuff = stuffMapper.findByField(".byId", number).get(0);
                            movingInfo.setStuffByStuffId(stuff);
                            break;
                        }
                    }
                    movingInfoMapper.edit(movingInfo);
                    break;

                }
                case 0:
                {
                    return;
                }

            }

        }
    }

    public static void OrdersMenu() throws ParseException {
        OrdersMapper ordersMapper = new OrdersMapper();
        DriversMapper driversMapper = new DriversMapper();
        ClientsMapper clientsMapper = new ClientsMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add order");
            System.out.println("4. Delete order");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Orders> ordersList = ordersMapper.findAll();
                    System.out.println(Orders.toString(ordersList));
                    break;
                }
                case 2: {
                    System.out.println("Search by:");
                    for (int i = 0; i < ordersTable.length; i++) {
                        System.out.println((i + 1) + ". " + ordersTable[i]);
                    }

                    System.out.println((ordersTable.length + 1) + ". Client by order id");

                    System.out.println((ordersTable.length + 2) + ". Driver by order id");

                    try {
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < ordersTable.length) {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Orders> result = ordersMapper.findByField(".by" + ordersTable[num], choice);
                        System.out.println(Orders.toString(result));
                        break;
                    }

                    if (num == ordersTable.length){
                        System.out.println("Enter order id");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        Orders result = ordersMapper.findByField(".byId", choice).get(0);
                        List<Clients> resultList = clientsMapper.findByField(".byId", result.getClientsByClient().getId());
                        System.out.println(Clients.toString(resultList));
                    }

                    if (num == ordersTable.length + 1){
                        System.out.println("Enter order id");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        Orders result = ordersMapper.findByField(".byId", choice).get(0);
                        List<Drivers> resultList = driversMapper.findByField(".byId", result.getDriversByDriver().getId());
                        System.out.println(Drivers.toString(resultList));
                    }

                    break;
                }
                case 3:
                {
                    String choice;
                    Orders orders = new Orders();

                    System.out.println("Enter order date");
                    choice = in.nextLine();
                    choice = in.nextLine();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    java.util.Date date = formatter.parse(choice);
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    orders.setDate(sqlDate);

                    System.out.println("Enter submission address");
                    choice = in.nextLine();

                    orders.setSubmissionAddress(choice);

                    System.out.println("Enter destination address");
                    choice = in.nextLine();

                    orders.setDestinationAddress(choice);

                    System.out.println("Enter date order execution");
                    choice = in.nextLine();

                    String pattern = "yyyy-MM-dd HH:mm:ss.S";
                    DateTimeFormatter formatterts = DateTimeFormatter.ofPattern(pattern);
                    Timestamp localDateTime = Timestamp.valueOf(LocalDateTime.from(formatterts.parse(choice)));
                    orders.setDateOrderExecution(localDateTime);

                    System.out.println("Enter price");
                    int number = in.nextInt();
                    orders.setPrice(number);

                    System.out.println(driversMapper.findAll());

                    System.out.println("Enter driver id");
                    number = in.nextInt();
                    Drivers drivers = driversMapper.findByField(".byId", number).get(0);
                    orders.setDriversByDriver(drivers);

                    System.out.println(Clients.toString(clientsMapper.findAll()));

                    System.out.println("Enter client id");
                    number = in.nextInt();
                    Clients clients = clientsMapper.findByField(".byId", number).get(0);
                    orders.setClientsByClient(clients);

                    ordersMapper.save(orders);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter order id");
                    int choice = in.nextInt();
                    Orders orders = ordersMapper.findByField(".byId", choice).get(0);
                    ordersMapper.delete(orders);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter order id");
                    int number = in.nextInt();
                    Orders orders = ordersMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Order date");
                    System.out.println("2. Submission address");
                    System.out.println("3. Destination address");
                    System.out.println("4. Date order execution");
                    System.out.println("5. Price");
                    System.out.println("6. Driver id");
                    System.out.println("7. Client id");


                    try{
                        number = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    String choice;

                    switch (number){
                        case 1:{
                            System.out.println("Enter order date");
                            choice = in.nextLine();
                            choice = in.nextLine();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            java.util.Date date = formatter.parse(choice);
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            orders.setDate(sqlDate);
                            break;
                        }
                        case 2:{
                            System.out.println("Enter submission address");
                            choice = in.nextLine();

                            orders.setSubmissionAddress(choice);
                            break;
                        }
                        case 3:{
                            System.out.println("Enter destination address");
                            choice = in.nextLine();

                            orders.setDestinationAddress(choice);
                            break;
                        }
                        case 4:{
                            System.out.println("Enter date order execution");
                            choice = in.nextLine();

                            String pattern = "MMM dd, yyyy HH:mm:ss.SSSSSSSS";
                            DateTimeFormatter formatterts = DateTimeFormatter.ofPattern(pattern);
                            Timestamp localDateTime = Timestamp.valueOf(LocalDateTime.from(formatterts.parse(choice)));
                            orders.setDateOrderExecution(localDateTime);
                            break;
                        }
                        case 5:{
                            System.out.println("Enter price");
                            number = in.nextInt();
                            orders.setPrice(number);
                        }
                        case 6:{
                            System.out.println(Drivers.toString(driversMapper.findAll()));

                            System.out.println("Enter driver id");
                            number = in.nextInt();
                            Drivers drivers = driversMapper.findByField(".byId", number).get(0);
                            orders.setDriversByDriver(drivers);
                        }
                        case 7:{
                            System.out.println(Clients.toString(clientsMapper.findAll()));
                            System.out.println("Enter client id");
                            number = in.nextInt();
                            Clients clients = clientsMapper.findByField(".byId", number).get(0);
                            orders.setClientsByClient(clients);
                        }
                    }
                    ordersMapper.edit(orders);
                    break;

                }

                case 0:
                {
                    return;
                }

            }

        }
    }

    public static void StuffMenu() throws ParseException {
        StuffMapper stuffMapper = new StuffMapper();
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("1. Show all table");
            System.out.println("2. Search");
            System.out.println("3. Add stuff");
            System.out.println("4. Delete stuff");
            System.out.println("5. Edit");
            System.out.println("0. Exit");

            try{
                num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    List<Stuff> stuffList = stuffMapper.findAll();
                    System.out.println(Stuff.toString(stuffList));
                    break;
                }
                case 2: {
                    System.out.println("Search by:");
                    for (int i = 0; i < stuffTable.length; i++) {
                        System.out.println((i + 1) + ". " + stuffTable[i]);
                    }

                    try {
                        num = in.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        throw new RuntimeException(e);
                    }

                    if (num > 0 && num < stuffTable.length) {
                        System.out.println("Enter searching value");
                        String choice = in.nextLine();
                        choice = in.nextLine();
                        List<Stuff> result = stuffMapper.findByField(".by" + stuffTable[num], choice);
                        System.out.println(Stuff.toString(result));
                        break;
                    }



                    break;
                }
                case 3:
                {
                    String choice;
                    Stuff stuff = new Stuff();

                    System.out.println("Enter surname");
                    choice = in.nextLine();
                    choice = in.nextLine();
                    stuff.setSurname(choice);

                    System.out.println("Enter name");
                    choice = in.nextLine();
                    stuff.setName(choice);

                    System.out.println("Enter middle name");
                    choice = in.nextLine();
                    stuff.setMiddleName(choice);

                    System.out.println("Enter date of birth");
                    choice = in.nextLine();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                    java.util.Date date = formatter.parse(choice);
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    stuff.setDateOfBirth(sqlDate);

                    System.out.println("Enter address");
                    choice = in.nextLine();
                    stuff.setAddress(choice);

                    System.out.println("Enter position");
                    choice = in.nextLine();
                    stuff.setPosition(choice);

                    System.out.println("Enter salary");
                    int number = in.nextInt();
                    stuff.setSalary(number);

                    stuffMapper.save(stuff);

                    break;
                }
                case 4:
                {
                    System.out.println("Enter stuff id");
                    int choice = in.nextInt();
                    Stuff stuff = stuffMapper.findByField(".byId", choice).get(0);
                    DriversMapper driversMapper = new DriversMapper();
                    boolean flag = false;
                    List<Drivers> driversList = driversMapper.findAll();
                    for (int i = 0; i < driversList.size(); i++){
                        if  (driversList.get(i).getStuffByStuff().getId() == stuff.getId()) flag = true;
                    }

                    if (flag) System.out.println("You can't delete this item");
                    else stuffMapper.delete(stuff);

                    break;
                }
                case 5:
                {
                    System.out.println("Enter stuff id");
                    int number = in.nextInt();
                    Stuff stuff = stuffMapper.findByField(".byId", number).get(0);

                    System.out.println("Edit:");
                    System.out.println("1. Surname");
                    System.out.println("2. Name");
                    System.out.println("3. Middle name");
                    System.out.println("4. Birthday");
                    System.out.println("5. Address");
                    System.out.println("6. Position");
                    System.out.println("7. Salary");


                    try{
                        number = in.nextInt();
                    } catch (InputMismatchException e){
                        throw new RuntimeException(e);
                    }

                    String choice;

                    switch (number){
                        case 1:{
                            System.out.println("Enter surname");
                            choice = in.nextLine();

                            stuff.setSurname(choice);
                            break;
                        }
                        case 2:{
                            System.out.println("Enter name");
                            choice = in.nextLine();

                            stuff.setName(choice);
                            break;
                        }
                        case 3:{
                            System.out.println("Enter middle name");
                            choice = in.nextLine();

                            stuff.setMiddleName(choice);
                            break;
                        }
                        case 4:{
                            System.out.println("Enter date of birth");
                            choice = in.nextLine();
                            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                            java.util.Date date = formatter.parse(choice);
                            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                            stuff.setDateOfBirth(sqlDate);
                            break;
                        }
                        case 5:{
                            System.out.println("Enter address");
                            choice = in.nextLine();
                            choice = in.nextLine();

                            stuff.setAddress(choice);
                            break;
                        }
                        case 6:{
                            System.out.println("Enter position");
                            choice = in.nextLine();
                            choice = in.nextLine();

                            stuff.setPosition(choice);
                            break;
                        }
                        case 7:{
                            System.out.println("Enter salary");
                            number = in.nextInt();
                            stuff.setSalary(number);
                            break;
                        }
                    }
                    stuffMapper.edit(stuff);
                    break;

                }
                case 0:
                {
                    return;
                }

            }

        }
    }
    public static void MainMenu() throws ParseException {
        int num;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("Choose a table:");
            System.out.println("1. Cargo type");
            System.out.println("2. Cars");
            System.out.println("3. Clients");
            System.out.println("4. Condition");
            System.out.println("5. Drivers");
            System.out.println("6. Moving information");
            System.out.println("7. Orders");
            System.out.println("8. Stuff");
            System.out.println("0. Exit");

            try{
               num = in.nextInt();
            } catch (InputMismatchException e){
                throw new RuntimeException(e);
            }

            switch (num){
                case 1:
                {
                    CargoTypeMenu();
                    break;
                }
                case 2:
                {
                    CarsMenu();
                    break;
                }
                case 3:
                {
                    ClientsMenu();
                    break;
                }
                case 4:
                {
                    ConditionMenu();
                    break;
                }
                case 5:
                {
                    DriversMenu();
                    break;
                }
                case 6:
                {
                    MovingInfoMenu();
                    break;
                }
                case 7:
                {
                    OrdersMenu();
                    break;
                }
                case 8:
                {
                    StuffMenu();
                    break;
                }
                case 0:
                {
                    return;
                }


            }

        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        MainMenu();
    }
}
