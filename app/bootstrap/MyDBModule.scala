package bootstrap

import com.google.inject.AbstractModule

class MyDBModule extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[InitialData]).asEagerSingleton()
  }

}
