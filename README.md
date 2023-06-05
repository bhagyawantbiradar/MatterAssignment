# MatterAssignment
This is simple 2048 game. To include the game in your project, follow the steps below.
  1. Add the JitPack repository to your build file 
      
      allprojects {
		      repositories {
			    ...
			    maven { url 'https://jitpack.io' }
		  }
	}
  
  2. Add the dependency
    
    dependencies {
	        implementation 'com.github.bhagyawantbiradar:MatterAssignment:Tag'
	  }
    
   3. Launch the game activity and pass the size of row and column
    
    val startGameIntent = Intent(this, GameActivity::class.java)
    startGameIntent.putExtra(Constants.ROW_COL_SIZE,4)
    startActivity(startGameIntent)
    
    That's it. you are ready to play the game.
