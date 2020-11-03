package com.polarquant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class PrestoStartedExampleApplication implements CommandLineRunner{

	@Autowired
	JdbcTemplate prestoJdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(PrestoStartedExampleApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		String sql = "select * from db_31abd9593e9983ec.event_vd where ds=20201029 limit 1";
		List<Map<String, Object>> results =  prestoJdbcTemplate.queryForList(sql);

		if(results !=null && results.size() >0){
			results.get(0).keySet().stream().forEach(k ->{
				System.out.println(k);
			});
		}
	}
}
