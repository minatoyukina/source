package com.example.demo.service.impl;

import com.example.demo.entity.Isolation;
import com.example.demo.mapper.IsolationMapper;
import com.example.demo.service.IsolationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chenchuanqi
 * @version 1.0
 * @since 2021/10/13 14:38
 */
@Service
public class IsolationServiceImpl implements IsolationService {

    @Resource
    private IsolationMapper isolationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = org.springframework.transaction.annotation.Isolation.SERIALIZABLE)
    public void update1(Long id, Double amount) {
        Isolation isolation = isolationMapper.getById(1L);
        sleep(30);
        isolationMapper.update(id, isolation.getAmount() + amount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update2(Long id, Double amount) {
        Isolation isolation = isolationMapper.getById(1L);
        sleep(1);
        isolationMapper.update(id, isolation.getAmount() + amount);
    }

    // dead lock. 子线程等待主线程结束, 然而主线程无法结束
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void nested() {
        isolationMapper.update(1L, 10d);
        Thread thread = new Thread(() -> isolationMapper.update(1L, 10d));
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int sec) {
        try {
            Thread.sleep(sec * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
