package sk.kosickaakademia.martinek.company.enumartoris;


public enum Gender {
    MALE(0), FEMALE(1), OTHER(2);

    private int value;
    Gender(int value){
        this.value=value;
    }

    Gender(Gender gender){
        this.value=gender.getValue();
    }

    public int getValue(){
        return value;
    }




}
