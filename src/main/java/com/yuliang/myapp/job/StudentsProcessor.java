package com.yuliang.myapp.job;

import com.google.common.util.concurrent.RateLimiter;

import com.yuliang.myapp.domain.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by yuliangjin (530367387@qq.com) on 18/1/10.
 *
 * @description This is a class to show how to process chuck of data the write way.
 */
public abstract class StudentsProcessor implements Worker{

  protected String workerName;
  protected int workerNum;
  protected int readPageSize = 500;  //一次读取列表大小
  protected double permitsPerSecond = 2000;  //限流

  protected RateLimiter rateLimiter;
  protected ThreadPoolExecutor pool;

  public void init() {
    pool = new ThreadPoolExecutor(workerNum, workerNum+1, 5, TimeUnit.MINUTES, new ArrayBlockingQueue<Runnable>(1000));
    rateLimiter = RateLimiter.create(permitsPerSecond);//Ratelimiter
//    logger.info("worker:{} rateLimiter init permitsPerSecond:{}", workerName, permitsPerSecond);
  }

  /**
   * Process students say 500 with some threads.
   * @param students raw data
   * @param workerNum thread numbers
   * @return
   */
  public List<String> process(List<Student> students, int workerNum) throws InterruptedException, ExecutionException {

    //Count how many Students each thread should process
    int threadLen = (students.size() + workerNum - 1) / workerNum;

    //Target tasks.
    List<Callable<List<String>>> tasks = new ArrayList<>(workerNum);

    int start = 0, end = 0;
    List<Student> workerList;
    for (int id=0; id < workerNum; id++) {
      start = id * threadLen;
      end = (id+1) * threadLen;
      if (start>=students.size()) {
        workerList = Collections.emptyList();
      } else {
        if (end>students.size()) {
          end = students.size();
        }
        workerList = students.subList(start, end);
      }
      tasks.add(new WorkerCallable(id, workerList));
    }
    List<String> result = new ArrayList<>(200);

    List<Future<List<String>>> futures = pool.invokeAll(tasks);
    for (Future<List<String>> future: futures) {
      List<String> list = future.get();
      if(list!=null && list.size()>0) {
        result.addAll(list);
      }
    }
    return result;
  }

  /**
   * Callable with data.
   */
  private class WorkerCallable implements Callable<List<String>> {
    private int id;
    private List<Student> jobList;

    public WorkerCallable(int id, List<Student> jobList) {
      this.id = id;
      this.jobList = jobList;
    }

    @Override
    public List<String> call() throws Exception {
      if (jobList==null || jobList.isEmpty()) {
        //log.info("Worker:{} job list is empty", id);
        return Collections.EMPTY_LIST;
      }
      List<String> result = new ArrayList<>();
      for(int i = 0; i < jobList.size(); i++) {
        List<String> list = doProcessOne(jobList.get(i));
        if(list!=null && list.size()>0) {
          rateLimiter.acquire(list.size());
          result.addAll(list);
        }
      }
      return result;
    }
  }

  //Logic to process one item
  private List<String> doProcessOne(Student student) {
    return Collections.emptyList();
  }

}
