package prjmercadocaiçara.util.facade;

public class DisplayComboBox
{
    private String displayMember;
    private String valueMember;

    public DisplayComboBox(String displayMember, String valueMember)
    {
        this.displayMember = displayMember;
        this.valueMember = valueMember;
    }

    public String getDisplayMember()
    { return displayMember; }

    public void setDisplayMember(String displayMember)
    { this.displayMember = displayMember; }

    public String getValueMember()
    { return valueMember; }

    public void setValueMember(String valueMember)
    { this.valueMember = valueMember; }

    @Override
    public String toString()
    {
        return displayMember;
    }
}