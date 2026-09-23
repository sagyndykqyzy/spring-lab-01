package kz.iitu.springlab.web;

import kz.iitu.springlab.config.AppProperties;
import kz.iitu.springlab.config.EnvironmentBanner;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/lab3")
public class ConfigController {

    private final AppProperties props;
    private final EnvironmentBanner banner;
    private final Environment environment;

    public ConfigController(
            AppProperties props,
            EnvironmentBanner banner,
            Environment environment) {
        this.props = props;
        this.banner = banner;
        this.environment = environment;
    }

    @GetMapping("/config")
    public Map<String, Object> config() {

        Map<String, Object> result = new LinkedHashMap<>();

        result.put("owner", props.getOwner());
        result.put("group", props.getGroup());

        result.put("mailFrom", props.getMail().getFrom());
        result.put("mailRetryCount", props.getMail().getRetryCount());
        result.put("mailTimeout", props.getMail().getTimeout().toString());
        result.put("mailEnabled", props.getMail().isEnabled());

        // Security configuration
        result.put(
                "securityTokenTtl",
                props.getSecurity().getTokenTtl().toString()
        );

        result.put(
                "securityMinPasswordLength",
                props.getSecurity().getMinPasswordLength()
        );

        result.put(
                "serverPort",
                environment.getProperty("server.port", "8080")
        );

        result.put(
                "activeProfiles",
                Arrays.asList(environment.getActiveProfiles())
        );

        result.put("banner", banner.describe());

        return result;
    }
}