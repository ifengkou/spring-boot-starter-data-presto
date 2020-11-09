package com.polarquant;

import com.polarquant.data.autoconfigure.manage.QueryManager;
import com.polarquant.data.autoconfigure.manage.annotation.EnableQueryManager;
import com.polarquant.data.autoconfigure.manage.exception.PrestoQueryManageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@EnableQueryManager
@SpringBootApplication
public class PrestoStartedExampleApplication implements CommandLineRunner {

    @Autowired
    JdbcTemplate prestoJdbcTemplate;

    @Autowired
    QueryManager queryManager;

    public static void main(String[] args) {
        SpringApplication.run(PrestoStartedExampleApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        String taskId = "abcd1234";
        queryManager.startQueryTask(taskId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    queryManager.asyncKill(taskId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (PrestoQueryManageException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        String sql = "with event as (\n" +
                "    select event.\"xwhat_id\" as \"event.xwhat_id\",event.\"xwhat\" as \"event.xwhat\",event.\"ds\" as \"event.ds\",event.\"distinct_id\" as \"event.distinct_id\",event.\"xwhen\" as \"event.xwhen\",date_trunc('day',from_unixtime(event.\"xwhen\"/1000)) as statTime\n" +
                "    from hive.db_guiyinpertest.event_vd event\n" +
                "\n" +
                "        where  event.ds between 20200520 and 20200823\n" +
                "            and (1=1)\n" +
                "\n" +
                "    )\n" +
                "select array_fill(97,coalesce(date_diff('day', parse_datetime('20200520','YYYYMMdd'),statTime)+1,0),\"$$value1\") as \"$Anything.general-0\",array_fill(97,coalesce(date_diff('day', parse_datetime('20200520','YYYYMMdd'),statTime)+1,0),\"$$value2\") as \"$startup.unique-1\",max(\"$$value1\") as \"max-$Anything.general-0\",max(\"$$value2\") as \"max-$startup.unique-1\",max(detailCount) as maxDetailCount\n" +
                "from (\n" +
                "    select statTime,  ( count(\"event.distinct_id\")) as \"$$value1\" , null as \"$$value2\" , 1 as detailCount  from event where  1=1  group by  ROLLUP(statTime) union all select statTime,   null as \"$$value1\" ,( count(distinct \"event.distinct_id\") ) as \"$$value2\" , 1 as detailCount  from event where (\"event.xwhat_id\"=21) group by  ROLLUP(statTime)\n" +
                ")";
        //String sql = "select * from hive.db_autotest052.event_vd limit 10";

        List<Map<String, Object>> results = prestoJdbcTemplate.queryForList(sql);

        results.stream().forEach(m -> {
            m.keySet().stream().forEach(k -> {
                System.out.print(k + "," + m.get(k) + " ");
            });
            System.out.println();
        });

        queryManager.taskComplete();

    }


}
