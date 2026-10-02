package kz.iitu.springlab.web;
import org.springframework.aop.support.AopUtils;
import java.util.Map;
import kz.iitu.springlab.service.CatalogService;
import org.springframework.web.bind.annotation.*;
import org.springframework.aop.support.AopUtils;
import java.util.Map;
import java.util.List;
import kz.iitu.springlab.aspect.SlowMethodAspect;
@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;
    private final SlowMethodAspect slowMethodAspect;
    public CatalogController(CatalogService catalogService,
                             SlowMethodAspect slowMethodAspect) {
        this.catalogService = catalogService;
        this.slowMethodAspect = slowMethodAspect;
    }

    @GetMapping("/item/{id}")
    public String findById(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/items")
    public List<String> findAll(
            @RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }
    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
                "className", catalogService.getClass().getName(),
                "superClass", catalogService.getClass().getSuperclass().getSimpleName(),
                "isAopProxy", String.valueOf(AopUtils.isAopProxy(catalogService)),
                "isCglib", String.valueOf(AopUtils.isCglibProxy(catalogService))
        );
    }
    @DeleteMapping("/item/{id}")
    public String remove(@PathVariable long id) {
        return catalogService.remove(id);
    }

    @GetMapping("/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }
    @GetMapping("/slow-calls")
    public List<String> slowCalls() {
        return slowMethodAspect.getSlowCalls();
    }
}