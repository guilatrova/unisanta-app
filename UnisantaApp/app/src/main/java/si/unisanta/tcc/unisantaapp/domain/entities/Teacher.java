package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

public class Teacher extends SugarRecord{
    private String name;

    public Teacher()
    {

    }

    public Teacher(String nameTeacher)
    {
        name = nameTeacher;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        //Abreviar nome do professor até o segundo nome
        int amountSpaces = name.length() - name.replaceAll(" ", "").length();
        if (amountSpaces > 2) {
            int secondSpace = name.indexOf(" ");
            secondSpace = name.indexOf(" ", secondSpace+1);

            int lastSpace = secondSpace;
            while (name.indexOf(" ", lastSpace + 1) > -1) {
                lastSpace = name.indexOf(" ", lastSpace + 1);
            }

            return name.substring(0, secondSpace) + name.substring(lastSpace);
        }
        return getName();
    }

    public void setName(String nameTeacher) {
        name = nameTeacher;
    }
}
