package utils;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;


public class Configuration {

    public static Supplier<Config> conf = Suppliers.memoize(new Supplier<Config>(){
        public Config get(){
            return ConfigFactory
                    .systemProperties()
                    .withFallback(ConfigFactory.systemEnvironment())
                    .withFallback(ConfigFactory.parseResourcesAnySyntax("application",ConfigParseOptions.defaults()))
                    .resolve(ConfigResolveOptions.noSystem());
        }
    });

    public static Object getAt(String configName){
        return conf.get().getAnyRef(configName);
    }
    public static String getConfig(String configName){
        return conf.get().getString(configName);
    }
    public static int getInt(String configName){
        return conf.get().getInt(configName);
    }

}
