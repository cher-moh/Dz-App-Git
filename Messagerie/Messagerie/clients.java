package Messagerie;


public class clients   {
	String id_sender;
	int i;
	Boolean etat = false;
	String id_Getter;

	public clients(String sender, Boolean et, int n,String getter) {
		this.id_sender = sender;
		this.i = n;
		this.etat=et;
		this.id_Getter=getter;
	}
public Boolean getEtat() {
	return etat;
}public String getId_Getter() {
	return id_Getter;
}public String getId_sender() {
	return id_sender;
}public void setId_Getter(String id_Getter) {
	this.id_Getter = id_Getter;
}public void setId_sender(String id_sender) {
	this.id_sender = id_sender;
}
public void setI(int i) {
	this.i = i;
}
public void setEtat(Boolean etat) {
	this.etat = etat;
}
public int getI() {
	return i;
}
}
