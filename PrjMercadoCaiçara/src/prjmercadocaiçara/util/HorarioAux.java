package prjmercadocaiçara.util;

public class HorarioAux
{
    private String diasemana, turno, horini, horfim;

    public HorarioAux(String diasemana, String turno, String horini, String horfim)
    {
        this.diasemana = diasemana;
        this.turno = turno;
        this.horini = horini;
        this.horfim = horfim;
    }
    
    public HorarioAux()
    { this("", "", "", ""); }

    public String getDiasemana()
    { return diasemana; }

    public void setDiasemana(String diasemana)
    { this.diasemana = diasemana; }

    public String getTurno()
    { return turno; }

    public void setTurno(String turno)
    { this.turno = turno; }

    public String getHorini()
    { return horini; }

    public void setHorini(String horini)
    { this.horini = horini; }

    public String getHorfim()
    { return horfim; }

    public void setHorfim(String horfim)
    { this.horfim = horfim; }
}