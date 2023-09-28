Zoznam zmie oproti semestrálke z minulého semestra:
- rozloženie triedy Chessboard na dve triedy AnimationChessboard a PrintOutChessboard
	- pôvodná trieda ostala ako abstraktná
	- metódy a časti konštruktora ktoré boli spoločné ostali v danej triede, všetky ostatné metódy
	  sa presunuly do svojích tried
		- toto odtránilo problém ktorý vznikal pri inicializácií triedy Chessboard. V predošlej verzí
		  pri inicializácií došlo aj k vzniku objektu FileTool ktorý si automaticky vytvoril zložku na 
		  ukladanie riešení aj keď program bežal v režime animácie pri ktorom sa žiadné riešenia neukladajú.
	- výrazná redukcia duplicitných kódov
- rozšírenie triedy FileTool na viac typov výstupu ako len .txt súbory
	- výrazná redukcia duplicitných kódov
	- vylepšené adresovanie výnimiek
- úprava triedy Main
	- najvýraznejšia redukcia duplicitných kódov
	- optimalizácia
	- zvýšené zapezpečenie proti chýbnému vstupu od užívateľa ktorý by mohol mať za následok pád programu

