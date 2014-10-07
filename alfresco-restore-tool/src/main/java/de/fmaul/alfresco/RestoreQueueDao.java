package de.fmaul.alfresco;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class RestoreQueueDao {

	private JdbcDaoSupport jdbcDaoSupport;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcDaoSupport = new SimpleJdbcDaoSupport();
		jdbcDaoSupport.setDataSource(dataSource);
	}

	public List<RestoreJob> getJobs() {
		return jdbcDaoSupport
				.getJdbcTemplate()
				.query("SELECT id,stringval1,url FROM aat_batch_jobs WHERE status IS NULL ORDER BY ID",
						new RowMapper<RestoreJob>() {
							@Override
							public RestoreJob mapRow(ResultSet resultSet, int column) throws SQLException {
								RestoreJob restoreJob = new RestoreJob();
								restoreJob.setId(resultSet.getLong(1));
								restoreJob.setNodeRef(resultSet.getString(2));
								restoreJob.setFilePath(resultSet.getString(3));
								return restoreJob;
							}
						});

	}

	public void setStatus(Long id, String status) {
		jdbcDaoSupport.getJdbcTemplate().update("UPDATE aat_batch_jobs SET status=? WHERE id=?", status, id);
	}
}
