package si.unisanta.tcc.unisantaapp.domain.entities;

import com.orm.SugarRecord;

import si.unisanta.tcc.unisantaapp.domain.valueobjects.Time;

public class Courseware extends SugarRecord {
    private Subject subject;

    private String title;
    private String description;
    private String uploadDate;
    private String uploadTime;
    private String size;

    private String downloadLink;

    public Courseware() {
    }

    public Courseware(Subject subject, String title, String description, String uploadDate, Time uploadTime, String size, String downloadLink) {
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.uploadDate = uploadDate;
        this.uploadTime = uploadTime.toString();
        this.size = size;
        this.downloadLink = downloadLink;
    }

    public Subject getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public String getSize() {
        return size;
    }

    public Time getUploadTime() {
        return new Time(uploadTime);
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public String getFileName() {
        int typeStart = title.lastIndexOf('(');

        String extension = title.substring(typeStart+1, title.length()-1).toLowerCase();
        String name = title.substring(0, typeStart-1).replace(" ", "_");
        return name + "." + extension;
    }

    public String getExtension() {
        String filename = getFileName();
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setUploadTime(Time t) {
        uploadTime = t.toString();
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public int calcSizeInBytes() {
        String unit = size.substring(size.length()-2);
        int whiteSpace = size.indexOf(" ");
        float rawSize = Float.parseFloat(size.substring(0, whiteSpace).replace(",", "."));

        if (unit.equals("MB"))
            return (int)(rawSize * 1000000);
        if (unit.equals("KB"))
            return (int)(rawSize * 1000);
        return (int)rawSize;
    }
}
