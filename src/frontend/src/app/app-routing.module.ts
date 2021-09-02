import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {IdeasResolver} from "./shared/resolvers/ideas.resolver";
import {IdeasComponent} from "./ideas/ideas.component";
import {CreateIdeaComponent} from "./create-idea/create-idea.component";
import {IdeaFillComponent} from "./idea-fill/idea-fill.component";
import {IdeaResolver} from "./shared/resolvers/idea.resolver";
import {HomeComponent} from "./home/home.component";
import {TagResolver} from "./shared/resolvers/tag.resolver";
import {UpdateIdeaComponent} from "./update-idea/update-idea.component";
import {AuthGuard} from "./auth.guard";
import {LoginComponent} from "./login/login.component";
import {RegistrationComponent} from "./registration/registration.component";
import {UserComponent} from "./user/user.component";
import {UserResolver} from "./shared/resolvers/user.resolver";
import {CurrentUserResolver} from "./shared/resolvers/current-user.resolver";
import {SettingsComponent} from "./settings/settings.component";
import {ProfileComponent} from "./settings/profile/profile.component";
import {PostsResolver} from "./shared/resolvers/posts.resolver";

const routes: Routes = [
    {
        path: '',
        component: HomeComponent,
        data: { animation: 'home' },
        canActivate: [AuthGuard],
        resolve: {
            currentUser: CurrentUserResolver
        },
        children: [
            {
                path: 'ideas',
                component: IdeasComponent,
                data: { animation: 'ideas' },
                resolve: {
                    ideas: IdeasResolver,
                    currentUser: CurrentUserResolver
                },
            },
            {
                path: 'ideas/id/:id',
                component: IdeaFillComponent,
                data: { animation: 'idea' },
                resolve: {
                    idea: IdeaResolver,
                    currentUser: CurrentUserResolver
                }
            },
            {
                path: 'ideas/create',
                component: CreateIdeaComponent,
                data: { animation: 'idea-create' },
                resolve: {
                    tags: TagResolver
                }
            },
            {
                path: 'ideas/update/:id',
                component: UpdateIdeaComponent,
                data: { animation: 'update-idea' },
                resolve: {
                    idea: IdeaResolver
                }
            },
            {
                path: 'profile',
                component: UserComponent,
                data: { animation: 'profile' },
                resolve: {
                    currentUser: CurrentUserResolver,
                },
            },
            {
                path: 'settings',
                component: SettingsComponent,
                data: { animation: 'settings' },
                resolve: {
                    currentUser: CurrentUserResolver
                },
                children: [
                    {
                        path: 'profile',
                        component: ProfileComponent,
                        resolve: {
                            currentUser: CurrentUserResolver,
                            posts: PostsResolver,
                        }
                    }
                ]
            },
            {
                path: 'user/:id',
                component: UserComponent,
                data: { animation: 'user' },
                resolve: {
                    user: UserResolver
                }
            },
        ]
    },
    {
        path: 'auth',
        component: LoginComponent,
        data: { animation: 'auth' }
    },
    {
        path: 'registration',
        component: RegistrationComponent,
        data: { animation: 'registration' },
        resolve: {
            posts: PostsResolver
        }
    },
]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {

}