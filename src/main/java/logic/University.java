package logic;

import java.util.ArrayList;
import java.util.List;

public class University {
    public List<Department> departmentList = new ArrayList<>();

    public void load() {
        for (Department department : departmentList) {
            department.load();
        }
    }

    public void init() {
        Department csDepartment = new Department("CS");
        departmentList.add(csDepartment);

        Teacher ali = new Teacher("ali", "hello");
        ali.roomId = "12512";
        ali.name = "Ali";
        ali.degree = TeacherDegree.teacher3;
        csDepartment.teacherList.add(ali);
        csDepartment.setAdmin(ali);

        Teacher beh = new Teacher("beh", "hello");
        beh.roomId = "85858";
        beh.name = "BEHRAD";
        beh.degree = TeacherDegree.teacher2;
        csDepartment.teacherList.add(beh);
        csDepartment.setAssistant(beh);

        Teacher yasna = new Teacher("yasna", "hello");
        yasna.roomId = "89123";
        yasna.name = "Yasna";
        yasna.degree = TeacherDegree.teacher1;
        csDepartment.teacherList.add(yasna);

        Student arian = new Student("arian", "hello");
        arian.studentNumber = "31141";
        arian.setGuide(yasna);
        arian.name = "Arian Fazli";
        arian.degree = Degree.BACHELOR;
        csDepartment.studentList.add(arian);

        Student pari = new Student("pari", "hello");
        pari.studentNumber = "6652";
        pari.setGuide(beh);
        pari.name = "PARIA";
        pari.degree = Degree.PHD;
        csDepartment.studentList.add(pari);

        Student arman = new Student("arman", "hello");
        arman.name = "Arman Kesh";
        arman.setGuide(beh);
        arman.degree = Degree.MASTERSHIP;
        csDepartment.studentList.add(arman);

        Course ap = new Course("Adv Pr", beh);
        ap.unitCount = 4;
        ap.degree = Degree.BACHELOR;
        ap.addStudent(arian);
        ap.addStudent(pari);
        ap.addStudent(arman);
        csDepartment.courseList.add(ap);

        Course feminism = new Course("Feminism", yasna);
        feminism.unitCount = 9;
        feminism.degree = Degree.MASTERSHIP;
        feminism.addStudent(arian);
        feminism.addStudent(pari);
        feminism.addStudent(arman);
        csDepartment.courseList.add(feminism);

        Department ceDepartment = new Department("C Eng");
        departmentList.add(ceDepartment);

        Teacher faranak = new Teacher("faranak", "hello");
        faranak.name = "Faranak Faranaki";
        faranak.degree = TeacherDegree.teacher2;
        ceDepartment.teacherList.add(faranak);
        ceDepartment.setAdmin(faranak);

        Teacher kiana = new Teacher("kiana", "hello");
        kiana.name = "Kiana Kianaii";
        kiana.degree = TeacherDegree.teacher2;
        ceDepartment.teacherList.add(kiana);
        ceDepartment.setAssistant(kiana);

        Student mamad = new Student("mamad", "hello");
        mamad.name = "Mamad Mamadi";
        mamad.degree = Degree.MASTERSHIP;
        ceDepartment.studentList.add(mamad);

        Course network = new Course("Network+", faranak);
        network.unitCount = 3;
        network.degree = Degree.BACHELOR;
        ceDepartment.courseList.add(network);
        network.addStudent(mamad);
        network.addStudent(arian);

        Course os = new Course("OS", kiana);
        os.unitCount = 5;
        ceDepartment.courseList.add(os);
        os.addStudent(pari);
        os.degree = Degree.MASTERSHIP;

        Department eeDep = new Department("Electronic Engineering");
        departmentList.add(eeDep);

        Teacher zahra = new Teacher("zahra", "hello");
        zahra.degree = TeacherDegree.teacher3;
        zahra.name = "Zahra zahraii";
        eeDep.teacherList.add(zahra);
        eeDep.setAssistant(zahra);

        Teacher atarodi = new Teacher("atarodi", "hello");
        atarodi.name = "Dr Atarodi";
        atarodi.degree = TeacherDegree.teacher2;
        eeDep.teacherList.add(atarodi);
        eeDep.setAdmin(atarodi);

        Student mahsa = new Student("mahsa", "hello");
        mahsa.name = "Mahsa Mahsaii";
        mahsa.degree = Degree.BACHELOR;
        eeDep.studentList.add(mahsa);

        Course logicalCirc = new Course("Logical Circuts", zahra);
        logicalCirc.unitCount = 2;
        eeDep.courseList.add(logicalCirc);
        logicalCirc.addStudent(mahsa);
        logicalCirc.addStudent(pari);

        Course oop = new Course("OOP", atarodi);
        oop.unitCount = 7;
        eeDep.courseList.add(oop);
        oop.addStudent(arian);
        oop.addStudent(mamad);

        Department physicsDep = new Department("Physics");
        departmentList.add(physicsDep);

        Teacher rahele = new Teacher("rahele", "hello");
        rahele.name = "Rahele raheleii";
        rahele.roomId = "234jf";
        physicsDep.teacherList.add(rahele);
        eeDep.setAdmin(rahele);


        Teacher davood = new Teacher("davood", "hello");
        davood.name = "Davood Davoody";
        davood.roomId = "2342";
        physicsDep.teacherList.add(davood);
        physicsDep.setAssistant(davood);

        Student bami = new Student("bami", "hello");
        bami.name = "Bamdad";
        bami.degree = Degree.PHD;
        physicsDep.studentList.add(bami);

        Course physic = new Course("Gen Physic", davood);
        physic.unitCount = 2;
        physicsDep.courseList.add(physic);
        physic.addStudent(mamad);
        physic.addStudent(arian);

        Department chemisDep = new Department("Chemistry Dep");
        departmentList.add(chemisDep);

        Teacher horse = new Teacher("horse", "hello");
        horse.name = "Horse Horsy";
        horse.roomId = "2341";
        chemisDep.teacherList.add(horse);
        chemisDep.setAdmin(horse);

        Teacher duck = new Teacher("duck", "hello");
        duck.name = "Duck Ducky";
        duck.roomId = "234111";
        duck.degree = TeacherDegree.teacher2;
        chemisDep.teacherList.add(duck);
        chemisDep.setAssistant(duck);

        Student naomi = new Student("naomi", "hello");
        naomi.name = "Naomi Naomii";
        naomi.degree = Degree.BACHELOR;
        chemisDep.studentList.add(naomi);

        Course chemAli = new Course("Chemistry Ali", duck);
        chemAli.unitCount = 1;
        chemisDep.courseList.add(chemAli);
        chemAli.addStudent(pari);
        chemAli.addStudent(naomi);

        Course baseChem = new Course("Basic Chemistry", duck);
        baseChem.unitCount = 3;
        chemisDep.courseList.add(baseChem);
        baseChem.addStudent(naomi);
        baseChem.addStudent(bami);


    }

}
