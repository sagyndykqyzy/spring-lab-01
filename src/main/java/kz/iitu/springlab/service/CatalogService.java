package kz.iitu.springlab.service;

import org.springframework.stereotype.Service;
import kz.iitu.springlab.audit.Audited;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.context.annotation.Lazy;
@Service
public class CatalogService {
    private final CatalogService self;

    public CatalogService(@Lazy CatalogService self) {
        this.self = self;
    }
    public String findById(long id) {
        sleep(50);
        return "Item no. " + id;
    }
    public String removeTwice(long id) {
        String first = self.remove(id);
        String second = self.remove(id + 1);
        return first + "; " + second;
    }
    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        sleep(300);
        return IntStream.rangeClosed(1, limit)
                .mapToObj(i -> "Item no. " + i)
                .toList();
    }

    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid identifier: " + id);
        }
        return "Removed item no. " + id;
    }

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}