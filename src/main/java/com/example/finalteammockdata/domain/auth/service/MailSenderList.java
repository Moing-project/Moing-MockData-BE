package com.example.finalteammockdata.domain.auth.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class MailSenderList {
    private final List<String> email_list;
    private Thread thread;
    private static MailSenderList instance;
    private final AuthServiceHelper authServiceHelper;

    private MailSenderList(AuthServiceHelper authServiceHelper) {
        this.authServiceHelper = authServiceHelper;
        this.email_list = new LinkedList<>();
        this.thread = new Thread(MailSenderList::sendMailForAll);
        instance = this;
    }

    private void mailOn() {
        if (!this.thread.isAlive()) {
            this.thread = new Thread(MailSenderList::sendMailForAll);
            this.thread.start();
        }
    }

    public static MailSenderList getInstance() {
        if (instance == null)
            throw new RuntimeException("Instance Error");
        return instance;
    }

    public void addMailReceiver(String address) {
        synchronized (this.email_list) {
            System.out.println("add = " + address);
            this.email_list.add(address);
            mailOn();
        }
    }

    public List<String> getMailReceiver() {
        if (this.email_list.size() == 0)
            return null;
        synchronized (this.email_list) {
            log.debug("get Mail List");
            List<String> copy = List.copyOf(this.email_list);
            this.email_list.clear();
            return copy;
        }
    }

    protected void sendMailAll() {
        synchronized (this.email_list) {
            authServiceHelper.sendMailToList(this.email_list);
            this.email_list.clear();
        }
    }
    public static void sendMailForAllTest() {
        log.info("active mail sender Thread");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return;
        }
        StringBuilder sb = new StringBuilder();
        MailSenderList.getInstance().getMailReceiver().forEach(e -> sb.append(String.format("%s\n", e)));
        System.out.println("sb = " + sb);
    }

    public static void sendMailForAll() {
        log.info("active mail sender Thread");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            return;
        }
        log.info("send mail for All");
        MailSenderList.getInstance().sendMailAll();
    }

}