[Setup]
AppName=MysqlJavaCat
AppVersion=0.2.18
DefaultDirName={pf}\MysqlJavaCat
DefaultGroupName=MysqlJavaCat
OutputDir=Z:\var\projects\MysqlJavaCat\Win
OutputBaseFilename=mysqljavacat_win_dist
SetupIconFile=Z:\var\projects\MysqlJavaCat\Win\MysqlJavaCat.ico
Compression=lzma
SolidCompression=yes

[Files]
Source: "Z:\var\projects\MysqlJavaCat\dist\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs; Excludes: "MysqlJavaCat.jar"
Source: "Z:\var\projects\MysqlJavaCat\Win\MysqlJavaCat.exe"; DestDir: "{app}";

[Tasks]
Name: desktopicon; Description: {cm:CreateDesktopIcon}; GroupDescription: {cm:AdditionalIcons}; Flags: unchecked

[Icons]
Name: "{group}\MysqlJavaCat"; Filename: "{app}\MysqlJavaCat.exe"
Name: "{userdesktop}\MysqlJavaCat"; Filename: "{app}\MysqlJavaCat.exe"; Tasks: desktopicon