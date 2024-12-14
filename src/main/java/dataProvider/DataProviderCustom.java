package dataProvider;

import org.testng.annotations.DataProvider;

public class DataProviderCustom {
    @DataProvider
    public Object[][] offersDataProvider(){
        return new Object[][]{
                {"ასტრა პარკი | Astra Park", 30, 5 },
                {"ლისი ლემანსი | Lisi Lemans", 50, 10},
                {"World Fitness", 100, 35},
                {"სკინანე | Skinane", 90, 18},
                {"კოლიზეუმ ჯიმ |Colloseum Gym", 70, 21},
                {"დიელ ფიტნესი | DL fitness", 85, 35},
                {"მანდალა სამგორის ფილიალი | Mandala", 140, 28},
                {"რანჩო პალომინო | Palomino Ranch", 70, 20},
                {"Sportscape - პადელის ღია კორტები", 500, 125},
                {"არენა II | Arena II Sports Complex", 200, 40}

        };


    }

    @DataProvider
    public Object[][] offerNames() {
        return new Object[][]{
                {"ასტრა პარკი | Astra Park"},
                {"ლისი ლემანსი | Lisi Lemans"},
                {"World Fitness"},
                {"სკინანე | Skinane"},
                {"კოლიზეუმ ჯიმ |Colloseum Gym"},
                {"დიელ ფიტნესი | DL fitness"},
                {"მანდალა სამგორის ფილიალი | Mandala"},
                {"რანჩო პალომინო | Palomino Ranch"},
                {"არენა II | Arena II Sports Complex"},
                {"სითი სპორტი | City Sport"}

        };
    }


    // - Fill First Name, Last Name , Gender and mobile number dynamically using @Parameters
    @DataProvider
    public Object[][] studentsForm(){
        return new Object[][]{
                {"Flora", "Ayvazyan", "Female", "2342362362" },
                {"Giorgi", "Eliava", "Male", "2332342365" },
                {"Sophie", "Chapidze", "Female", "7002572365" }

        };
    }
}
