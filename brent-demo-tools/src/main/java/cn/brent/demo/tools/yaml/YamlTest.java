package cn.brent.demo.tools.yaml;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

public class YamlTest {

	@Test
	public void parse(){
		//初始化Yaml解析器
        Yaml yaml = new Yaml();
        //读入文件
        Object result= yaml.load(Yaml.class.getResourceAsStream("/cn/brent/demo/tools/yaml/init.yml"));
        System.out.println(result.getClass());
        System.out.println(result);
	}
}
