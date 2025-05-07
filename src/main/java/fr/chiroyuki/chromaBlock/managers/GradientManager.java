package fr.chiroyuki.chromaBlock.managers;

import fr.chiroyuki.chromaBlock.data.Gradient;

import java.util.*;

public class GradientManager {
    private final Map<String, Gradient> gradients = new HashMap<>();

    public boolean addGradient(String name, Gradient gradient) {
        if (gradients.containsKey(name.toLowerCase())) {
            return false;
        }
        gradients.put(name.toLowerCase(), gradient);
        return true;
    }

    public void deleteGradient(String name) {
        gradients.remove(name.toLowerCase());
    }

    public Gradient getGradient(String name) {
        return gradients.get(name.toLowerCase());
    }

    public Map<String, Gradient> listGradients() {
        return gradients;
    }

    public boolean exists(String name) {
        return gradients.containsKey(name.toLowerCase());
    }

    public Collection<String> getGradientNames() {
        return gradients.keySet();
    }
}
