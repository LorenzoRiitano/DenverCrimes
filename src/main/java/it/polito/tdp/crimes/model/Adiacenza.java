package it.polito.tdp.crimes.model;

public class Adiacenza {
	private String v1;
	private String v2;
	private double peso;
	public Adiacenza(String v1, String v2, double peso) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}

	public final String getV1() {
		return v1;
	}
	public final void setV1(String v1) {
		this.v1 = v1;
	}
	public final String getV2() {
		return v2;
	}
	public final void setV2(String v2) {
		this.v2 = v2;
	}
	public final double getPeso() {
		return peso;
	}
	public final void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((v1 == null) ? 0 : v1.hashCode());
		result = prime * result + ((v2 == null) ? 0 : v2.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adiacenza other = (Adiacenza) obj;
		if (v1 == null) {
			if (other.v1 != null)
				return false;
		} else if (!v1.equals(other.v1))
			return false;
		if (v2 == null) {
			if (other.v2 != null)
				return false;
		} else if (!v2.equals(other.v2))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return  v1 + " - " + v2 + " - " + peso+"\n";
	}
	
	

}
