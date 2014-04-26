package com.github.jankroken.ircclient.protocol.domain

trait Target {
}

class UnknownTarget(val id:String) extends Target {
  override def toString = "UnknownTarget(%s)".format(id)
}

object Target {

	def getTargets(targetString: String, server: IRCServer): Array[Target] = {
		val targets = targetString.split(',').map(getTarget(_,server))
//		print("Targets:")
//		for (val target ← targets) print(" "+target)
//		println
		targets
	}
 
	protected def getTarget(targetString: String, server: IRCServer): Target = {
		val ChannelMatch = "^([#&+!][^@.]*)$".r
		val UserHostServerMatch = "^(..*)%(..*)@(.*$)".r
		val UserNickHostMatch = "^..*!..*@.*$".r
    val UserHost = "^.[^%!]*@.*$".r
    val TargetMask = "[#$].*\\..*$".r
    val Nickname = "^(.*$)".r

    targetString match {
      case ChannelMatch(channelString) ⇒ println("match:ChannelMatch: "+targetString)
      case UserHostServerMatch(user,host,nick) ⇒ println("match:UserHostServerMatch: "+targetString)
      case UserNickHostMatch(userNickHost) ⇒ println("match:UserNickHostMatch: "+targetString)
      case UserHost(userHost) ⇒ println("match:UserHost: "+targetString)
      case TargetMask(mask) ⇒ println("match:TargetMatch: "+targetString)
      case Nickname(nickname) ⇒ return new Nick(targetString)
      case _ ⇒ println("match: no match: "+targetString)
    }
          
          
          /*
  msgto      =  channel OK 
             | user [ "%" host ] "@" servername OK
             | user "%" host  OK
             | targetmask OK
             | nickname 
             | nickname "!" user "@" host OK
  channel    =  ( "#" / "+" / ( "!" channelid ) / "&" ) chanstring
                [ ":" chanstring ] "OK?"
  servername =  hostname
  host       =  hostname / hostaddr
  hostname   =  shortname *( "." shortname )
  shortname  =  ( letter / digit ) *( letter / digit / "-" )
                *( letter / digit )
                  ; as specified in RFC 1123 [HNAME]
  hostaddr   =  ip4addr / ip6addr
  ip4addr    =  1*3digit "." 1*3digit "." 1*3digit "." 1*3digit
  ip6addr    =  1*hexdigit 7( ":" 1*hexdigit )
  ip6addr    =/ "0:0:0:0:0:" ( "0" / "FFFF" ) ":" ip4addr
  nickname   =  ( letter / special ) *8( letter / digit / special / "-" )
  targetmask =  ( "$" / "#" ) mask
                  ; see details on allowed masks in section 3.3.1
  chanstring =  %x01-07 / %x08-09 / %x0B-0C / %x0E-1F / %x21-2B
  chanstring =/ %x2D-39 / %x3B-FF
                  ; any octet except NUL, BELL, CR, LF, " ", "," and ":"
  channelid  = 5( %x41-5A / digit )   ; 5( A-Z / 0-9 )

                                     
  user       =  1*( %x01-09 / %x0B-0C / %x0E-1F / %x21-3F / %x41-FF )
                  ; any octet except NUL, CR, LF, " " and "@"
  key        =  1*23( %x01-05 / %x07-08 / %x0C / %x0E-1F / %x21-7F )
                  ; any 7-bit US_ASCII character,
                  ; except NUL, CR, LF, FF, h/v TABs, and " "
  letter     =  %x41-5A / %x61-7A       ; A-Z / a-z
  digit      =  %x30-39                 ; 0-9
  hexdigit   =  digit / "A" / "B" / "C" / "D" / "E" / "F"
  special    =  %x5B-60 / %x7B-7D
                   ; "[", "]", "\", "`", "_", "^", "{", "|", "}"

                                     */
  
		val target:Target = targetString match {
		  case ChannelMatch(channelString) ⇒ new Channel(channelString, server)
		  case UserNickHostMatch(nickString,userString,hostString) ⇒ new UnknownTarget(targetString)
		  case _ ⇒ new UnknownTarget(targetString)
		}
     
		target
	}
}

 /*
  msgto      =  channel / ( user [ "%" host ] "@" servername )
  msgto      =/ ( user "%" host ) / targetmask
  msgto      =/ nickname / ( nickname "!" user "@" host )
  channel    =  ( "#" / "+" / ( "!" channelid ) / "&" ) chanstring
                [ ":" chanstring ]
  servername =  hostname
  host       =  hostname / hostaddr
  hostname   =  shortname *( "." shortname )
  shortname  =  ( letter / digit ) *( letter / digit / "-" )
                *( letter / digit )
                  ; as specified in RFC 1123 [HNAME]
  hostaddr   =  ip4addr / ip6addr
  ip4addr    =  1*3digit "." 1*3digit "." 1*3digit "." 1*3digit
  ip6addr    =  1*hexdigit 7( ":" 1*hexdigit )
  ip6addr    =/ "0:0:0:0:0:" ( "0" / "FFFF" ) ":" ip4addr
  nickname   =  ( letter / special ) *8( letter / digit / special / "-" )
  targetmask =  ( "$" / "#" ) mask
                  ; see details on allowed masks in section 3.3.1
  chanstring =  %x01-07 / %x08-09 / %x0B-0C / %x0E-1F / %x21-2B
  chanstring =/ %x2D-39 / %x3B-FF
                  ; any octet except NUL, BELL, CR, LF, " ", "," and ":"
  channelid  = 5( %x41-5A / digit )   ; 5( A-Z / 0-9 )
*/
