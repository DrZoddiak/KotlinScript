onPlayerJoin {
    Logger.info("Player joined the server")
    //Scripts can use methods available fron the event
    val player = player()
    val name = player.get(Keys.DISPLAY_NAME).get()
    player.sendMessage(Component.text("Welcome ").append(name))
    Server.broadcastAudience().sendMessage(name.append(Component.text(" joined the server!")))
}