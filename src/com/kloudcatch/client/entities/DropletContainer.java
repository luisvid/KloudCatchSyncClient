package com.kloudcatch.client.entities;

import java.util.ArrayList;
import java.util.List;

public class DropletContainer {
	private List<Droplet> droplets;

	public DropletContainer() {
		droplets = new ArrayList<Droplet>();
	}

	public int size() {
		return droplets.size();
	}

	public void setDroplets(List<Droplet> droplets) {
		this.droplets = droplets;
	}

	public List<Droplet> getDroplets() {
		return droplets;
	}
}
