package si.unisanta.tcc.unisantaapp.domain.dto;

public class SubjectGradeDto {
    private String subject;
    private int status;
    private boolean dp;

    private float p1;
    private float p2;
    private float p3;
    private float finalScore;

    public SubjectGradeDto(String subject, int status, boolean dp, float p1, float p2, float p3, float finalScore) {
        this.subject = subject;
        this.status = status;
        this.dp = dp;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.finalScore = finalScore;
    }

    public String getSubject() {
        return subject;
    }

    public int getStatus() {
        return status;
    }

    public boolean isDP() {
        return dp;
    }

    public float getP1() {
        return p1;
    }

    public float getP2() {
        return p2;
    }

    public float getP3() {
        return p3;
    }

    public float getFinalScore() {
        return finalScore;
    }
}
