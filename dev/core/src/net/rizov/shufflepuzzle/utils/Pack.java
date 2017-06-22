package net.rizov.shufflepuzzle.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Pack {

    private List<ImageDescriptor> imageDescriptors;

    private int index = 0;

    public Pack(PackList packList) {
        imageDescriptors = listImages(packList.list());
    }

    public int size() {
        return imageDescriptors.size();
    }

    public ImageDescriptor next() {
        if (index >= size()) {
            index = 0;
        }

        return get(index++);
    }

    public ImageDescriptor random() {
        Random random = new Random();
        index = random.nextInt(size());

        return get(index);
    }

    public ImageDescriptor current() {
        if (index >= size()) {
            index = 0;
        }

        return imageDescriptors.get(index);
    }

    public ImageDescriptor get(int index) {
        return imageDescriptors.get(index);
    }

    public boolean hasImages() {
        return size() > 0;
    }

    public Iterator<ImageDescriptor> iterator() {
        return imageDescriptors.iterator();
    }

    public String getAtlas() {
        return "pack/pack.pack";
    }

    public void positionTo(String id) {
        if (hasImages()) {
            do {
                if (current().getShortFileName().equals(id)) {
                    break;
                }
            } while (next() != null);
        }
    }

    public void positionTo(int index) {
        if (index >= 0 && index < size()) {
            this.index = index;
        }
    }

    private List<ImageDescriptor> listImages(String[] list) {

        List<ImageDescriptor> imageDescriptors = new ArrayList<ImageDescriptor>();

        /*
        FileHandle[] files = Gdx.files.internal("pack").list(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return !pathname.getName().startsWith("pack");
            }
        });

        Arrays.sort(files, new Comparator<FileHandle>() {
            @Override
            public int compare(FileHandle o1, FileHandle o2) {
                return o1.name().compareTo(o2.name());
            }
        });

        for (FileHandle file : files) {
            if (!file.isDirectory()) {
                imageDescriptors.add(new ImageDescriptor(file.name()));
            }
        }
        */

        for (String item : list) {
            imageDescriptors.add(new ImageDescriptor(item));
        }

        return imageDescriptors;
    }

}
