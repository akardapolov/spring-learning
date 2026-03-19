package com.example.springdatajdbc.config;

import com.example.springdatajdbc.entity.Category;
import org.springframework.context.event.EventListener;
import org.springframework.data.relational.core.mapping.event.AfterConvertEvent;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

@Component
public class CategoryLifecycleListener {

  private final LifecycleEventsCollector collector;

  public CategoryLifecycleListener(LifecycleEventsCollector collector) {
    this.collector = collector;
  }

  @EventListener
  public void onBeforeConvert(BeforeConvertEvent<Category> event) {
    collector.add("BeforeConvert: " + event.getEntity().getTitle());
  }

  @EventListener
  public void onBeforeSave(BeforeSaveEvent<Category> event) {
    collector.add("BeforeSave: " + event.getEntity().getTitle());
  }

  @EventListener
  public void onAfterSave(AfterSaveEvent<Category> event) {
    collector.add("AfterSave: " + event.getEntity().getTitle());
  }

  @EventListener
  public void onAfterConvert(AfterConvertEvent<Category> event) {
    collector.add("AfterConvert: " + event.getEntity().getTitle());
  }
}
