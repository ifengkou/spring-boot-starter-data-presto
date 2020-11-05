package com.polarquant.data.autoconfigure;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author shenlongguang<https://github.com/ifengkou>
 * @date: 2020/10/30
 */
@Data
@NoArgsConstructor
public class PrestoSessionProperties {
    /**
     * https://prestosql.io/docs/current/admin/properties-memory-management.html
     */
    String queryMaxTotalMemory;
    /**
     * https://prestosql.io/docs/current/admin/properties-query-management.html
     */
    String queryMaxRunTime;
    /**
     * https://prestosql.io/docs/current/admin/properties-query-management.html
     */
    String queryMaxExecutionTime;
}
