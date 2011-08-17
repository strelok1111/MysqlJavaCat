[Setup]
AppName=MysqlJavaCat
AppVersion=0.2.14
DefaultDirName={pf}\MysqlJavaCat
DefaultGroupName=MysqlJavaCat
OutputDir=Z:\MysqlJavaCat\Win
OutputBaseFilename=mysqljavacat_win_dist
SetupIconFile=Z:\MysqlJavaCat\Win\MysqlJavaCat.ico
Compression=lzma
SolidCompression=yes

[Files]
Source: "Z:\MysqlJavaCat\dist\*"; DestDir: "{app}"; Flags: ignoreversion recursesubdirs createallsubdirs
Source: "Z:\MysqlJavaCat\Win\MysqlJavaCat.exe"; DestDir: "{app}";

[Tasks]
Name: desktopicon; Description: {cm:CreateDesktopIcon}; GroupDescription: {cm:AdditionalIcons}; Flags: unchecked

[Icons]
Name: "{group}\MysqlJavaCat"; Filename: "{app}\MysqlJavaCat.exe"
Name: "{userdesktop}\MysqlJavaCat"; Filename: "{app}\MysqlJavaCat.exe"; Tasks: desktopicon