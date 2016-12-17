package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.application.UnisantaApplication;
import si.unisanta.tcc.unisantaapp.domain.valueobjects.SchoolYear;

public class Subject extends SugarRecord{
    public static final int APPROVED = 1;
    public static final int UNKNOWN = 0;
    public static final int DISAPPROVED = -1;

    private long originalId;
    private boolean dp;
    private int status;
    private String name;
    private String nickName;
    private String semester;
    private Teacher teacher;
    private String classGroup;

    public Subject() {
        this.status = UNKNOWN;
    }

    public Subject(long originalId, String name, boolean dp, String classGroup, SchoolYear schoolYear, Teacher teacher) {
        this.originalId = originalId;
        this.name = name;
        this.classGroup = classGroup;
        this.semester = schoolYear.toString();
        this.teacher = teacher;
        this.dp = dp;
        this.status = UNKNOWN;
    }

    public Subject(long originalId, String name, String nickname, boolean dp, String classGroup, SchoolYear schoolYear, Teacher teacher) {
        this.originalId = originalId;
        this.name = name;
        this.nickName = nickname;
        this.classGroup = classGroup;
        this.semester = schoolYear.toString();
        this.teacher = teacher;
        this.dp = dp;
        this.status = UNKNOWN;
    }

    public long getOriginalId() {
        return originalId;
    }

    public String getName() {
        return name;
    }

    public String getClassGroup() {
        return classGroup;
    }

    public String getNickName() {
        if (nickName == null) {
            UnisantaApplication.Log_i(getName() + " não possui nickname");
            return name;
        }
        return nickName;
    }

    public SchoolYear getSchoolYear() {
        return new SchoolYear(semester);
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public boolean isDP() {
        return dp;
    }

    public int getStatus() {
        return status;
    }

    public void setDP(boolean dp) {
        this.dp = dp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        semester = schoolYear.toString();
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setClassGroup(String classGroup) {
        this.classGroup = classGroup;
    }

    public boolean sameIdentity(Subject subject) {
        return this.getId().equals(subject.getId());
    }

    @Override
    public String toString() {
        return name;
    }
}