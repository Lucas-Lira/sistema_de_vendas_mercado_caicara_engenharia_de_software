package prjmercadocaiçara.util.observer;

public interface Sujeito
{
    // MÉTODO(S) ABSTRATO(S):
    public abstract void register(Observador o);
    public abstract void remove(Observador o);
    public abstract void notify(String msg);
}