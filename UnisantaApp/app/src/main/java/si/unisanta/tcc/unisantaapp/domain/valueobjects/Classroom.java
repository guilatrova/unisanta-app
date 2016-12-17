package si.unisanta.tcc.unisantaapp.domain.valueobjects;

public class Classroom {
    private char block;
    private String room;

    public Classroom(char block, String room) {
        this.block = block;
        this.room = room;
    }

    public Classroom(String classroom) {
        if (classroom.length() > 1) {
            block = classroom.charAt(0);
            room = classroom.substring(1);
        }
        else {
            block = '-';
            room = classroom;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Classroom classroom = (Classroom) o;

        if (block != classroom.block) return false;
        return room.equals(classroom.room);

    }

    @Override
    public int hashCode() {
        int result = (int) block;
        result = 31 * result + (room != null ? room.hashCode() : 0);
        return result;
    }

    public char getBlock() {
        return block;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return block + room;
    }
}
