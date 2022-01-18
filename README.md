# Presentation

This project can be used by minecraft developers and minecraft players in order to play an hunger game. It proposes one command : <code>./hg</code> in order to create new hunger game configuration files.

# Download

On Windows there is a restriction regarding the maximal length of a path, that's why it is preferable to clone the [minecraft-platform](https://github.com/Pierre-Emmanuel41/minecraft-game-plateform/blob/master/README.md) project and run its <code>deploy.bat</code> file before cloning this project.

Then, according to the Minecraft API version there is on the server, you should download this project by specifying the branch associated to the associated version if supported. To do so, you can use the following command line :

```git
git clone -b 1.0_MC_1.13.2-SNAPSHOT https://github.com/Pierre-Emmanuel41/minecraft-border.git --recurse-submodules
```

and then double click on the deploy.bat file. This will deploy this project and all its dependencies on your computer. Which means it generates the folder associated to this project and its dependencies in your .m2 folder. Once this has been done, you can add the project as maven dependency on your maven project :

```xml
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>game</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>platform</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>border</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>rules</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
<dependency>
	<groupId>fr.pederobien.minecraft</groupId>
	<artifactId>hungergame</artifactId>
	<version>1.0_MC_1.13.2-SNAPSHOT</version>
	<scope>provided</scope>
</dependency>
```

This plugins depends on the minecraft-border and minecraft-rules plugins, that is why they should be present on the server plugins folder. Moreover, like them, this plugin should also be present on the minecraft server in order to be used by several other plugins. That is why it is declared as <code>provided</code> for the dependency scope.

To see the functionalities provided by this plugin, please have a look to [this presentation](https://github.com/Pierre-Emmanuel41/minecraft-hungergame/blob/1.0_MC_1.13.2-SNAPSHOT/Presentation.md)
